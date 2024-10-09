# 1. Introduction
## 1.1 Purpose
The purpose of this project is to develop a feed composition calculator using the Pearson Square method. The system will provide an API for livestock nutritionists or feed manufacturers to calculate the required proportions of various ingredients based on a target crude protein (CP) value. The goal is to deliver a balanced and cost-effective feed formulation for livestock.
### 1.1.1 Project Scope
***The project involves building a backend service with the following features:***
- An API that receives a batch number and target CP value as inputs and returns the appropriate ingredient proportions.
- An API that collects the values of atleast 1 Protein and atleast 1 Carbohydrate ingredient to create a total feed formulation with total quantity provided and totalCpValue of the formulation with values for addictive added respectfully.
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
    "formulationName": "",
    "quantity":,
    "targetCpValue":
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
    "formulationId": "",
    "formulationName": "",
    "date": "",
    "quantity": ,
    "targetCpValue": ,
    "ingredients": [
        {
            "name": "Soya beans",
            "crudeProtein": 44.0,
            "quantity": 
        },
        {
            "name": "Groundnuts",
            "crudeProtein": 45.0,
            "quantity": 
        },
        {
            "name": "Blood Meal",
            "crudeProtein": 80.0,
            "quantity": 
        },
        {
            "name": "Fish Meal",
            "crudeProtein": 65.0,
            "quantity": 
        },
        {
            "name": "Maize",
            "crudeProtein": 9.0,
            "quantity":
        },
        {
            "name": "Cassava",
            "crudeProtein": 2.0,
            "quantity": 
        },
        {
            "name": "Diphosphate Calcium",
            "crudeProtein": 0.0,
            "quantity": 
        },
        {
            "name": "Bone Meal",
            "crudeProtein": 0.0,
            "quantity": 
        },
        {
            "name": "Marine Shell Flour",
            "crudeProtein": 0.0,
            "quantity": 
        },
        {
            "name": "Salt",
            "crudeProtein": 0.0,
            "quantity": 
        },
        {
            "name": "Vitamin C",
            "crudeProtein": 0.0,
            "quantity": 
        },
        {
            "name": "Premix",
            "crudeProtein": 0.0,
            "quantity": 
        },
        {
            "name": "Palm Oil",
            "crudeProtein": 0.0,
            "quantity": 
        },
        {
            "name": "Anti-toxin",
            "crudeProtein": 0.0,
            "quantity": 
        }
    ]
}
    </code>
  </pre>
</div>

### GET request for all forulations
<div>
  <button onclick="copyToClipboard()"></button>
  <pre id="codeBlock">
    <code>
    http://localhost:8080/api/feed-formulation
    </code>
  </pre>
</div>

### GET request for a specific Formulation by ID and Date of creation
<div>
  <button onclick="copyToClipboard()"></button>
  <pre id="codeBlock">
    <code>
    http://localhost:8080/api/feed-formulation/{ID}/{DATE}
    </code>
  </pre>
</div>

### PUT to update a specific formulation
## PUT <div>
  <button onclick="copyToClipboard()"></button>
  <pre id="codeBlock">
    <code>
    http://localhost:8080/api/feed-formulation/{ID}/{DATE}
    </code>
  </pre>
</div>

### Request
<div>
  <button onclick="copyToClipboard()"></button>
  <pre id="codeBlock">
    <code>
    {
    "formulationName": "",
    "quantity":,
    "targetCpValue":
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
    "formulationId": "",
    "formulationName": "",
    "date": "",
    "quantity": ,
    "targetCpValue": ,
    "ingredients": [
        {
            "name": "Soya beans",
            "crudeProtein": 44.0,
            "quantity": 
        },
        {
            "name": "Groundnuts",
            "crudeProtein": 45.0,
            "quantity": 
        },
        {
            "name": "Blood Meal",
            "crudeProtein": 80.0,
            "quantity": 
        },
        {
            "name": "Fish Meal",
            "crudeProtein": 65.0,
            "quantity": 
        },
        {
            "name": "Maize",
            "crudeProtein": 9.0,
            "quantity":
        },
        {
            "name": "Cassava",
            "crudeProtein": 2.0,
            "quantity": 
        },
        {
            "name": "Diphosphate Calcium",
            "crudeProtein": 0.0,
            "quantity": 
        },
        {
            "name": "Bone Meal",
            "crudeProtein": 0.0,
            "quantity": 
        },
        {
            "name": "Marine Shell Flour",
            "crudeProtein": 0.0,
            "quantity": 
        },
        {
            "name": "Salt",
            "crudeProtein": 0.0,
            "quantity": 
        },
        {
            "name": "Vitamin C",
            "crudeProtein": 0.0,
            "quantity": 
        },
        {
            "name": "Premix",
            "crudeProtein": 0.0,
            "quantity": 
        },
        {
            "name": "Palm Oil",
            "crudeProtein": 0.0,
            "quantity": 
        },
        {
            "name": "Anti-toxin",
            "crudeProtein": 0.0,
            "quantity": 
        }
    ]
}
    </code>
  </pre>
</div>

### DELETE to delete a specific formulation
## DEL <div>
  <button onclick="copyToClipboard()"></button>
  <pre id="codeBlock">
    <code>
    http://localhost:8080/api/feed-formulation/{ID}/{DATE}
    </code>
  </pre>
</div>



# Endpoints for Custom backend http
### POST 
<div>
  <button onclick="copyToClipboard()"></button>
  <pre id="codeBlock">
    <code>
    http://localhost:8080/api/feed-formulations
    </code>
  </pre>
</div>

### Request
<div>
  <button onclick="copyToClipboard()"></button>
  <pre id="codeBlock">
    <code>
    {
  "formulationName": "Starter Feed 1",
  "proteins": [
    {
      "name": "Soya Beans",
      "quantityKg":
    },
    {
      "name": "Fish Meal",
      "quantityKg":
    }
  ],
  "carbohydrates": [
    {
      "name": "Maize",
      "quantityKg":
    },
    {
      "name": "Cassava",
      "quantityKg":
    }
  ]
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
    "formulationId": "",
    "formulationName": "",
    "date": "",
    "quantity": ,
    "targetCpValue": ,
    "ingredients": [
        {
            "name": "Soya beans",
            "crudeProtein": 44.0,
            "quantity": 
        },
        {
            "name": "Groundnuts",
            "crudeProtein": 45.0,
            "quantity": 
        },
        {
            "name": "Blood Meal",
            "crudeProtein": 80.0,
            "quantity": 
        },
        {
            "name": "Fish Meal",
            "crudeProtein": 65.0,
            "quantity": 
        },
        {
            "name": "Maize",
            "crudeProtein": 9.0,
            "quantity":
        },
        {
            "name": "Cassava",
            "crudeProtein": 2.0,
            "quantity": 
        },
        {
            "name": "Diphosphate Calcium",
            "crudeProtein": 0.0,
            "quantity": 
        },
        {
            "name": "Bone Meal",
            "crudeProtein": 0.0,
            "quantity": 
        },
        {
            "name": "Marine Shell Flour",
            "crudeProtein": 0.0,
            "quantity": 
        },
        {
            "name": "Salt",
            "crudeProtein": 0.0,
            "quantity": 
        },
        {
            "name": "Vitamin C",
            "crudeProtein": 0.0,
            "quantity": 
        },
        {
            "name": "Premix",
            "crudeProtein": 0.0,
            "quantity": 
        },
        {
            "name": "Palm Oil",
            "crudeProtein": 0.0,
            "quantity": 
        },
        {
            "name": "Anti-toxin",
            "crudeProtein": 0.0,
            "quantity": 
        }
    ]
}
    </code>
  </pre>
</div>

### GET request for all forulations
<div>
  <button onclick="copyToClipboard()"></button>
  <pre id="codeBlock">
    <code>
    http://localhost:8080/api/feed-formulation
    </code>
  </pre>
</div>

### GET request for a specific Formulation by ID and Date of creation
<div>
  <button onclick="copyToClipboard()"></button>
  <pre id="codeBlock">
    <code>
    http://localhost:8080/api/feed-formulation/{ID}/{DATE}
    </code>
  </pre>
</div>

### PUT to update a specific formulation
## PUT <div>
  <button onclick="copyToClipboard()"></button>
  <pre id="codeBlock">
    <code>
    http://localhost:8080/api/feed-formulation/{ID}/{DATE}
    </code>
  </pre>
</div>

### Request
<div>
  <button onclick="copyToClipboard()"></button>
  <pre id="codeBlock">
    <code>
    {
    "formulationName": "",
    "quantity":,
    "targetCpValue":
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
    "formulationId": "",
    "formulationName": "",
    "date": "",
    "quantity": ,
    "targetCpValue": ,
    "ingredients": [
        {
            "name": "Soya beans",
            "crudeProtein": 44.0,
            "quantity": 
        },
        {
            "name": "Groundnuts",
            "crudeProtein": 45.0,
            "quantity": 
        },
        {
            "name": "Blood Meal",
            "crudeProtein": 80.0,
            "quantity": 
        },
        {
            "name": "Fish Meal",
            "crudeProtein": 65.0,
            "quantity": 
        },
        {
            "name": "Maize",
            "crudeProtein": 9.0,
            "quantity":
        },
        {
            "name": "Cassava",
            "crudeProtein": 2.0,
            "quantity": 
        },
        {
            "name": "Diphosphate Calcium",
            "crudeProtein": 0.0,
            "quantity": 
        },
        {
            "name": "Bone Meal",
            "crudeProtein": 0.0,
            "quantity": 
        },
        {
            "name": "Marine Shell Flour",
            "crudeProtein": 0.0,
            "quantity": 
        },
        {
            "name": "Salt",
            "crudeProtein": 0.0,
            "quantity": 
        },
        {
            "name": "Vitamin C",
            "crudeProtein": 0.0,
            "quantity": 
        },
        {
            "name": "Premix",
            "crudeProtein": 0.0,
            "quantity": 
        },
        {
            "name": "Palm Oil",
            "crudeProtein": 0.0,
            "quantity": 
        },
        {
            "name": "Anti-toxin",
            "crudeProtein": 0.0,
            "quantity": 
        }
    ]
}
    </code>
  </pre>
</div>

### DELETE to delete a specific formulation
## DEL <div>
  <button onclick="copyToClipboard()"></button>
  <pre id="codeBlock">
    <code>
    http://localhost:8080/api/feed-formulation/{ID}/{DATE}
    </code>
  </pre>
</div>
