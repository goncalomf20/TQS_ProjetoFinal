import React from 'react';
import { HiShoppingCart } from 'react-icons/hi';
import { Link } from 'react-router-dom';

export function CartButton({ cartQuantity }) {
  return (
    <Link to="/checkout"> {/* Link to navigate to the checkout page */}
      <button
        className="fixed bottom-4 right-4 bg-blue-500 text-white p-3 rounded-full shadow-lg hover:bg-blue-600 flex items-center justify-center"
      >
        <HiShoppingCart className="w-6 h-6" />
        {cartQuantity > 0 && ( /* Display badge if there's at least one item in the cart */
          <span className="ml-2 bg-red-500 text-white text-xs rounded-full px-2 py-1">
            {cartQuantity}
          </span>
        )}
      </button>
    </Link>
  );
}
