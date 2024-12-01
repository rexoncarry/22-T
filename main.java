const express = require('express');
const fetch = require('node-fetch');
const app = express();

app.use(express.json());

app.post('/exchange-token', async (req, res) => {
    const { code } = req.body;

    try {
        const response = await fetch('https://authorization-server.com/token', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: new URLSearchParams({
                grant_type: 'authorization_code',
                code: code,
                redirect_uri: 'https://your-app.com/redirect',
                client_id: 'your-client-id',
                client_secret: 'your-client-secret',
            }),
        });

        const data = await response.json();
        if (response.ok) {
            res.json({ success: true, access_token: data.access_token });
        } else {
            res.status(400).json({ success: false, message: data.error_description || 'Unknown error' });
        }
    } catch (error) {
        res.status(500).json({ success: false, message: 'Server error' });
    }
});

const PORT = 3000;
app.listen(PORT, () => console.log(`Server running on port ${PORT}`));
