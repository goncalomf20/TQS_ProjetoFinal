import React, { useEffect, useState } from 'react';
import '../css/Receipt.css';

const Receipt = ({ total_price, orderId }) => {
  const [currentDate, setCurrentDate] = useState('');

  useEffect(() => {
    console.log(total_price);
    // Get the current date
    const date = new Date();
    const formattedDate = `${date.toLocaleDateString()} ${date.toLocaleTimeString()}`;
    setCurrentDate(formattedDate);
  }, [total_price]);

  return (
    <div id="showScroll" className="container">
      <div className="receipt">
        <h1 className="logo">DetiCafé</h1>
        <div className="address">Universidade de Aveiro, 3810-193 Aveiro</div>
        <div className="centerItem bold">
        </div>
        <div className="survey bold">
          <p className="surveyID"></p>
        </div>
        <div className="paymentDetails bold">
          <div className="detail">PRODUCTS</div>
          <div className="detail">{total_price}</div>
        </div>
        <div className="paymentDetails">
          <div className="detail">TAXES</div>
          <div className="detail">0.00</div>
        </div>
        <div className="paymentDetails">
          <div className="detail">TOTAL</div>
          <div className="detail">{total_price}</div>
        </div>
        <div className="receiptBarcode">
          <div className="barcode">Order ID #{orderId}</div>
        </div>
        <div className=" returnPolicy bold">
          Order made at: {currentDate}
        </div>
        <div className="feedback">
          <div className="break">*************************************</div>
          <p className="center">
            We would love to hear your feedback on your recent experience with us. This survey will only take 1 minute to complete.
          </p>
          <h3 className="clickBait">Share Your Feedback</h3>
          <h4 className="web">www.deticafe.com</h4>
          <p className="center">Hablamos español</p>
          <div className="break">*************************************</div>
        </div>
        <div id="coupons" className="coupons">
          <div className="couponContainer">
            <div className="discount">2.00€ off</div>
            <div className="discountDetails">2.00€ off on the next carniceiro</div>
            <div className="expiration">
              <div className="item bold">Expires 12/12/1994</div>
              <div className="item">Up to 2€ Value</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Receipt;
