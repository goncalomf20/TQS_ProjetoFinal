import { Sidebar } from "flowbite-react";
import { BiSolidDrink } from "react-icons/bi";
import { IoFastFood } from "react-icons/io5";
import { FaIceCream, FaHotdog } from "react-icons/fa";
import { GiPriceTag } from "react-icons/gi";
import React, { useState } from 'react';

export function SidebarDC() {
  const [isExpanded, setIsExpanded] = useState(false);

  const toggleSidebar = () => {
    setIsExpanded((prevExpanded) => !prevExpanded);
  };

  const sidebarStyle = isExpanded ? "w-40" : "w-14"; // Adjust width based on state

  return (
    <Sidebar
      aria-label="Sidebar with logo branding example"
      onClick={toggleSidebar} // Toggle sidebar on click
      className={`transition-all duration-200 ${sidebarStyle} flex flex-col items-center`} // Center the icons horizontally
      style={{ height: "100vh" }} // Ensure full height for vertical centering
    >
      <Sidebar.Items className="flex flex-col flex-1 justify-center"> {/* Vertical centering */}
        <Sidebar.ItemGroup>
          <Sidebar.Item
            href="#"
            icon={BiSolidDrink}
            className="w-full flex justify-center hover:bg-gray-100 dark:hover:bg-gray-800" // Center the icon
          >
            {isExpanded && <span className="px-2">Drinks</span>} {/* Show text only if expanded */}
          </Sidebar.Item>
          <Sidebar.Item
            href="#"
            icon={IoFastFood}
            className="w-full flex justify-center hover:bg-gray-100 dark:hover:bg-gray-800"
          >
            {isExpanded && <span className="px-2">Foods</span>}
          </Sidebar.Item>
          <Sidebar.Item
            href="#"
            icon={FaHotdog}
            className="w-full flex justify-center hover:bg-gray-100 dark:hover:bg-gray-800"
          >
            {isExpanded && <span className="px-2">Snacks</span>}
          </Sidebar.Item>
          <Sidebar.Item
            href="#"
            icon={FaIceCream}
            className="w-full flex justify-center hover:bg-gray-100 dark:hover:bg-gray-800"
          >
            {isExpanded && <span className="px-2">Desserts</span>}
          </Sidebar.Item>
          <Sidebar.Item
            href="#"
            icon={GiPriceTag}
            className="w-full flex justify-center hover:bg-gray-100 dark:hover:bg-gray-800"
          >
            {isExpanded && <span className="px-2">Promotions</span>}
          </Sidebar.Item>
        </Sidebar.ItemGroup>
      </Sidebar.Items>
    </Sidebar>
  );
}
