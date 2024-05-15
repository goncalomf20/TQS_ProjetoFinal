# Use an official Python runtime as a parent image
FROM python:3.8-slim

# Set the working directory in the container
WORKDIR /initdb

# Copy the Java application files to the container
COPY . .

# Install any needed packages specified in requirements.txt
RUN pip install -r requirements.txt

# Run script.py when the container launches
CMD ["python", "./populate.py"]
