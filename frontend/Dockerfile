# Use an official Node.js runtime as the base image
FROM node:17-alpine

# Set the working directory in the Docker image
WORKDIR /app

# Copy the application files to the Docker image
COPY package.json .


# Copy the rest of the application files to the Docker image
COPY . .


# Start the application

CMD ["npm", "install"]
CMD ["npm", "run", "dev"]