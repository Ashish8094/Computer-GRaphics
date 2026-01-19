// Function to fetch and display spots
async function loadSpots() {
    try {
        const response = await fetch('/api/spots');
        const spots = await response.json();
        const container = document.getElementById('spots-container');
        container.innerHTML = ''; // Clear previous
        spots.forEach(spot => {
            const div = document.createElement('div');
            div.className = `spot ${spot.status}`;
            div.innerHTML = `
                <p>Spot ${spot.id}</p>
                <p>${spot.status}</p>
                ${spot.status === 'free' ? `<button onclick="bookSpot(${spot.id})">Book</button>` : ''}
            `;
            container.appendChild(div);
        });
    } catch (error) {
        console.error('Error loading spots:', error);
    }
}

// Function to book a spot
async function bookSpot(id) {
    try {
        const response = await fetch(`/api/book/${id}`, { method: 'POST' });
        const result = await response.json();
        alert(result.message);
        loadSpots(); // Refresh display
    } catch (error) {
        console.error('Error booking spot:', error);
    }
}

// Load spots on page load and refresh every 5 seconds
loadSpots();
setInterval(loadSpots, 5000);