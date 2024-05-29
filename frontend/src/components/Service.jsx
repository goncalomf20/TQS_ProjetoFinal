import React from 'react';

const Service = () => {
    const [orders, setOrders] = React.useState([]);
    const dummyOrders = [
        { order_id: 1, table_id: 1, status: 'in progress' },
        { order_id: 2, table_id: 2, status: 'ready' },
        { order_id: 3, table_id: 3, status: 'canceled' },
        { order_id: 4, table_id: 4, status: 'ready' },
        { order_id: 5, table_id: 5, status: 'unknown' },
    ];

    React.useEffect(() => {
        setOrders(dummyOrders);
    }, []); // Ensure this effect only runs once to avoid infinite loops

    const getColorByStatus = (status) => {
        switch (status) {
            case 'in progress':
                return 'bg-yellow-500';
            case 'canceled':
                return 'bg-red-500';
            case 'ready':
                return 'bg-green-500';
            default:
                return 'bg-white'; // Default for any unknown status
        }
    };

    return (
        <>
            <div className="flex items-center justify-center pt-10">    
                <h1 className="text-4xl font-bold">Orders</h1>
            </div>
            <div className='grid p-20 gap-10 grid-cols-5'>
                {orders.map((order) => (
                    <a
                        key={order.order_id}
                        href="#"
                        className={`block max-w-sm p-6 border border-gray-200 rounded-lg shadow hover:bg-gray-100 ${getColorByStatus(order.status)}`}
                    >
                        <h5 className="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">
                            Order #{order.order_id}
                        </h5>
                        <p className="font-normal text-gray-700 dark:text-gray-400">
                            Table: {order.table_id}
                        </p>
                        <p className="font-normal text-gray-700 dark:text-gray-400">
                            Status: {order.status}
                        </p>
                    </a>
                ))}
            </div>
        </>
    );
};

export default Service;
