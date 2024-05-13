import { Sidebar } from "flowbite-react";
import {
  BiSolidDrink,
  BiChevronLeft,
} from "react-icons/bi";
import { IoFastFood } from "react-icons/io5";
import { FaIceCream, FaHotdog } from "react-icons/fa";
import { GiPriceTag, GiCakeSlice } from "react-icons/gi";
import { IoCafeSharp } from "react-icons/io5";
import { Link } from "react-router-dom"; // For navigation with React Router
import React from "react"; // No need for useState anymore

export function SidebarDC() {
  // Sidebar will always be expanded, so remove the state
  const sidebarStyle = "w-52"; // Fixed expanded width

  const items = [
    { id: 1, name: "Drinks", icon: BiSolidDrink },
    { id: 2, name: "Foods", icon: IoFastFood },
    { id: 3, name: "Snacks", icon: FaHotdog },
    { id: 4, name: "Desserts", icon: FaIceCream },
    { id: 5, name: "Pastry", icon: GiCakeSlice },
    { id: 6, name: "Coffee", icon: IoCafeSharp },
    { id: 7, name: "Promotions", icon: GiPriceTag },
  ];

  return (
    <Sidebar
      aria-label="Always expanded sidebar"
      className={`transition-all duration-200 ${sidebarStyle} flex flex-col`} // Always expanded
      style={{ height: "100vh" }} // Ensuring full height for sidebar
    >
      <Sidebar.Items className="flex flex-col flex-1 justify-start mt-10"> {/* Sidebar items */}
        <Sidebar.ItemGroup>
          {items.map((item) => (
            <Link
              key={item.id}
              to={`/?category=${item.id}`} // Update URL with query parameter
              className="w-full py-4 flex justify-start items-center hover:bg-gray-200 dark:hover:bg-gray-800"
            >
              <item.icon className="text-3xl" /> {/* Icon size */}
              <span className="px-4 text-lg"> {/* No condition for expansion */}
                {item.name} {/* Display item name */}
              </span>
            </Link>
          ))}
        </Sidebar.ItemGroup>
      </Sidebar.Items>
    </Sidebar>
  );
}
