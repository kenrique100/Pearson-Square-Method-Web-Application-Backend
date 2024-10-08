# 1. Introduction
## 1.1 Purpose
The purpose of this project is to develop a feed composition calculator using the Pearson Square method. The system will provide an API for livestock nutritionists or feed manufacturers to calculate the required proportions of various ingredients based on a target crude protein (CP) value. The goal is to deliver a balanced and cost-effective feed formulation for livestock.
### 1.1.1 Project Scope
***The project involves building a backend service with the following features:***
- An API that receives a batch number and target CP value as inputs and returns the appropriate ingredient proportions.
- An API that recieved the values of atleast 1 Protein and atleast 1 Carbohydrate ingredient to create a total feed formulation with total quantity provided and totalCpValue of the formulation with values for addictive added respectfully.
- A calculation algorithm based on the Pearson Square method, adapted for multiple ingredients.
- Validations and exception handling for the input values.
- JUnit test coverage for key functionality.
- A clear and maintainable codebase with a structured package design.

# 1.3. User Characteristics
- The application is made up of 2 models the default model that requires total quantity and totalCpvalue and the Custom with requires the values of atleast 1 Protein and atleast 1 Carbohydrate ingredient to create a total feed formulation with total quantity provided and totalCpValue of the formulation with values for addictive added respectfully.
- Users are knowledgeable in feed formulation and nutrient balance.
- Users expect accuracy in feed composition calculations.

***Below are the endpooints to be tested following CRUD for the various Http***
## Endpoints for Defualt backend http
### POST 
<div>
  <button onclick="copyToClipboard()"></button>
  <pre id="codeBlock">
    <code>
    http://localhost:8080/api/feed-formulation
    </code>
  </pre>
</div>

### Request
<div>
  <button onclick="copyToClipboard()"></button>
  <pre id="codeBlock">
    <code>
    {
    "formulationName": "Name",
    "quantity":500,
    "targetCpValue":40
    }
    </code>
  </pre>
</div>

### Response 
<div>
  <button onclick="copyToClipboard()"></button>
  <pre id="codeBlock">
    <code>
   {
    "formulationId": "74732",
    "formulationName": "Name",
    "date": "2024-10-08",
    "quantity": 0.0,
    "targetCpValue": 65.0,
    "ingredients": [
        {
            "name": "Soya beans",
            "crudeProtein": 0.0,
            "quantity": 0.0
        },
        {
            "name": "Groundnuts",
            "crudeProtein": 0.0,
            "quantity": 0.0
        },
        {
            "name": "Blood Meal",
            "crudeProtein": 0.0,
            "quantity": 0.0
        },
        {
            "name": "Fish Meal",
            "crudeProtein": 0.0,
            "quantity": 0.0
        },
        {
            "name": "Maize",
            "crudeProtein": 0.0,
            "quantity": 0.0
        },
        {
            "name": "Cassava",
            "crudeProtein": 0.0,
            "quantity": 0.0
        },
        {
            "name": "Diphosphate Calcium",
            "crudeProtein": 0.0,
            "quantity": 0.0
        },
        {
            "name": "Bone Meal",
            "crudeProtein": 0.0,
            "quantity": 0.0
        },
        {
            "name": "Marine Shell Flour",
            "crudeProtein": 0.0,
            "quantity": 0.0
        },
        {
            "name": "Salt",
            "crudeProtein": 0.0,
            "quantity": 0.0
        },
        {
            "name": "Vitamin C",
            "crudeProtein": 0.0,
            "quantity": 0.0
        },
        {
            "name": "Premix",
            "crudeProtein": 0.0,
            "quantity": 0.0
        },
        {
            "name": "Palm Oil",
            "crudeProtein": 0.0,
            "quantity": 0.0
        },
        {
            "name": "Anti-toxin",
            "crudeProtein": 0.0,
            "quantity": 0.0
        }
                ]
    }
    </code>
  </pre>
</div>

