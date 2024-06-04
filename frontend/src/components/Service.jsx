import React, { useState, useEffect } from 'react';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';

const Service = () => {
    const [orders, setOrders] = useState([]);
    const [client, setClient] = useState(null);
    const [connected, setConnected] = useState(false);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [selectedOrder, setSelectedOrder] = useState(null);
    const [selectedOrderId, setSelectedOrderId] = useState(null);

    useEffect(() => {
        if (!connected) {
            const socket = new SockJS('http://localhost:8080/ws');
            const stompClient = Stomp.over(socket);

            stompClient.connect({}, () => {
                console.log('Connected to the WebSocket server');
                stompClient.subscribe('/topic/orders', (message) => {
                    const order = JSON.parse(message.body);
                    console.log('Order received:', order);
                    setOrders((prev) => {
                        const existingOrderIndex = prev.findIndex(existingOrder => existingOrder.orderId === order.orderId);
                        if (existingOrderIndex !== -1) {
                            // Order with the same ID found, update its status
                            const updatedOrders = [...prev];
                            updatedOrders[existingOrderIndex].status = order.status;
                            return updatedOrders;
                        } else {
                            // Order with the same ID not found, add it to the list
                            return [...prev, order];
                        }
                    });
                });
                setClient(stompClient);
                setConnected(true);
            }, (error) => {
                console.error('Error connecting to WebSocket', error);
            });

            return () => {
                if (client) {
                    client.disconnect(() => {
                        console.log('Disconnected from WebSocket');
                    }, (error) => {
                        console.error('Error disconnecting from WebSocket', error);
                    });
                }
            };
        }
    }, [connected, client]);

    const getColorByStatus = (status) => {
        switch (status) {
            case 'PREPARING':
                return 'bg-yellow-500';
            case 'DELIVERED':
                return 'bg-red-500';
            case 'READY':
                return 'bg-green-500';
            default:
                return 'bg-white'; // Default for any unknown status
        }
    };

    const openModal = (order) => {
        setSelectedOrder(order);
        setSelectedOrderId(order.orderId);
        setIsModalOpen(true);
    };

    const closeModal = () => {
        setIsModalOpen(false);
        setSelectedOrder(null);
    };

    const updateOrderStatus = (status) => {
        if (selectedOrder) {
            const updatedOrder = { ...selectedOrder, status };
            setOrders((prevOrders) => prevOrders.map((order) => order.orderId === selectedOrderId ? updatedOrder : order));
            client.send('/app/wsorder', {}, JSON.stringify(updatedOrder));
            closeModal();
        }
    };

    // Filter out orders with status 'DELIVERED'
    const filteredOrders = orders.filter(order => order.status !== 'DELIVERED');

    return (
        <>
            <div className="flex items-center justify-center pt-10">
                <h1 className="text-4xl font-bold">Orders</h1>
            </div>
            <div className='grid p-20 gap-10 grid-cols-5'>
                {filteredOrders.map((order) => (
                    <div
                        key={order.orderId}
                        className={`block max-w-sm p-6 border border-gray-200 rounded-lg shadow hover:bg-gray-100 ${getColorByStatus(order.status)}`}
                    >
                        <h5 className="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">
                            Order #{order.orderId}
                        </h5>
                        <p className="font-normal text-gray-700 dark:text-gray-400">
                            Status: {order.status}
                        </p>
                        <button 
                            onClick={() => openModal(order)}
                            className="mt-2 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
                        >
                            Change Status
                        </button>
                    </div>
                ))}
            </div>

            {isModalOpen && (
                <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
                    <div className="bg-white p-6 rounded shadow-lg">
                        <h2 className="text-xl font-bold mb-4">Change Order Status</h2>
                        <div className="flex justify-around">
                            <button
                                className="bg-yellow-500 hover:bg-yellow-700 text-white font-bold py-2 px-4 rounded"
                                onClick={() => updateOrderStatus('PREPARING')}
                            >
                                Preparing
                            </button>
                            <button
                                className="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded"
                                onClick={() => updateOrderStatus('READY')}
                            >
                                Ready
                            </button>
                            <button
                                className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
                                onClick={() => updateOrderStatus('DELIVERED')}
                            >
                                Delivered
                            </button>
                        </div>
                        <button 
                            onClick={closeModal}
                            className="mt-4 bg-gray-500 hover:bg-gray-700 text-white font-bold py-2 px-4 rounded"
                        >
                            Close
                        </button>
                    </div>
                </div>
            )}
        </>
    );
};

export default Service;
