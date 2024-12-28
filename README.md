# LogisticManagementSystem
This project provides a comprehensive tool for calculating city-to-city distances and estimating transportation costs based on various factors. By integrating Google Maps APIs, the application determines both the air distance (using the Haversine formula) and the road distance (via Distance Matrix API). Additionally, it calculates transportation costs by considering product-specific attributes such as priority, weight, dimensions, and sensitivity with distinct multipliers.

Key Features:
Air Distance Calculation: Calculates the shortest distance between two cities using the Haversine formula.
Road Distance Calculation: Retrieves real-world driving distance using Google Distance Matrix API.
Cost Estimation: Computes transportation costs based on:
Priority: Higher priority products incur additional costs.
Weight and Dimensions: Larger and heavier items influence the cost calculation with specific coefficients.
Sensitivity: Fragile or sensitive items add a surcharge to ensure proper handling.
Custom Error Handling: Provides informative messages for invalid inputs and API errors.
Transportation Selection: Offers users the ability to choose between the fastest (air) or cheapest (road) travel method based on the calculated costs and distances.

Technologies Used:
Java: Core programming language.
Google Maps API: For geocoding and distance matrix services.
OkHttp: For handling HTTP requests.
Gson: For parsing JSON responses.
Object-Oriented Design: Encapsulates logic for cost calculations, user input, and transportation selection.

How to Run:
Clone the repository.
Replace the placeholders for GEOCODING_API_KEY and DISTANCE_MATRIX_API_KEY with your Google Maps API keys.
Compile and run the program in your preferred Java IDE.
Input the required city and product information when prompted, and the application will compute the distances and costs accordingly.
