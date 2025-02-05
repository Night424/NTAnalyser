import axios from 'axios';

const API_URL = 'http://localhost:8080';

export const getStatus = async() => {
    try {
        const response = await axios.get('${}API_URL/status');
        return response.data;
    } catch (error) {
        console.error('Error fetching status!', error);
    }
}