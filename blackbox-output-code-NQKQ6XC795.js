const express = require('express');
const path = require('path');
const app = express();
const port = 3000;

// In-memory "database" for parking spots (10 spots, each with id, status)
let parkingSpots = [];
for (let i = 1; i <= 10; i++) {
    parkingSpots.push({ id: i, status: 'free' }); // Start all free
}

// Middleware to parse JSON
app.use(express.json());
app.use(express.static('public')); // Serve frontend files

// API to get all parking spots
app.get('/api/spots', (req, res) => {
    res.json(parkingSpots);
});

// API to book a spot (mark as occupied)
app.post('/api/book/:id', (req, res) => {
    const id = parseInt(req.params.id);
    const spot = parkingSpots.find(s => s.id === id);
    if (spot && spot.status === 'free') {
        spot.status = 'occupied';
        res.json({ message: 'Booked successfully' });
    } else {
        res.status(400).json({ message: 'Spot not available' });
    }
});

// Simulate IoT sensor updates (randomly change statuses every 10 seconds)
setInterval(() => {
    parkingSpots.forEach(spot => {
        if (Math.random() > 0.7) { // 30% chance to toggle
            spot.status = spot.status === 'free' ? 'occupied' : 'free';
        }
    });
    console.log('Sensor update: Spots updated'); // Log for debugging
}, 10000);

// Start server
app.listen(port, () => {
    console.log(`Server running at http://localhost:${port}`);
});