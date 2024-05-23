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
import React, { useEffect } from "react"; // No need for useState anymore

export function SidebarDC() {
  const [categories, setCategories] = React.useState([]); // State for categories
  const sidebarStyle = "w-52"; // Fixed expanded width

  useEffect(() => {
    fetch("http://localhost:8080/api/category/getAllCategories")
      .then((response) => response.json())
      .then((data) => {
        console.log(data); // Log the data to see its structure
        setCategories(data); // Set categories from API response
      })
      .catch((error) => {
        console.error("Error fetching categories:", error);
      });
  }, []);
  

  return (
    <Sidebar
      aria-label="Always expanded sidebar"
      className={`transition-all duration-200 ${sidebarStyle} flex flex-col`} // Always expanded
      style={{ height: "100vh" }} // Ensuring full height for sidebar
    >
      <Sidebar.Items className="flex flex-col flex-1 justify-start mt-10"> {/* Sidebar items */}
        <Sidebar.ItemGroup>
          {categories.map((item) => (
            <Link
              key={item.categoryId}
              to={`/?category=${item.categoryId}`} // Update URL with query parameter
              className="w-full py-4 flex justify-start items-center hover:bg-gray-200 dark:hover:bg-gray-800"
            >
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