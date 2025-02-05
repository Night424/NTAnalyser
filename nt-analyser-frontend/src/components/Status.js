import React, { useEffect, useState } from 'react';
import { getStatus } from '../api';

const Status = () => {
    const [status, setStatus] = useState('');

    useEffect(() => {
        const fetchStatus = async () => {
            const data = await getStatus();
            setStatus(data.status);
        };
        fetchStatus();
    }, []);

    return (
        <div>
            <h1>Status</h1>
            <p>{status || 'loading...'}</p>
        </div>
    );
};

export default Status;