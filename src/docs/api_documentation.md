
# API Documentation

## UserController

### Get All Users

**Endpoint**: `GET /api/v1/user/users`

**Description**: Retrieve all users.

**Response**:

```json
[
    {
        "id": 1,
        "name": "John Doe",
        "email": "john@example.com"
    }
]
```

**Status Codes**:
- 200 OK: Successfully retrieved users.

### Register User

**Endpoint**: `POST /api/v1/user/register`

**Description**: Create a new user.

**Request Body**:
```json
{
    "name": "John Doe",
    "email": "john@example.com",
    "password": "password123"
}
```

**Response**:
```json
{
    "message": "Registered successfully",
    "data": {
        "id": 1,
        "name": "John Doe",
        "email": "john@example.com"
    }
}
```

**Status Codes**:
- 201 Created: Successfully registered user.
- 409 Conflict: User registration failed due to a conflict (e.g., email already exists).
- 502 Bad Gateway: General error occurred.

### Login User

**Endpoint**: `POST /api/v1/user/login`

**Description**: Login a user.

**Request Body**:
```json
{
    "email": "john@example.com",
    "password": "password123"
}
```

**Response**:
```json
{
    "message": "Login success",
    "data": {
        "id": 1,
        "name": "John Doe",
        "email": "john@example.com"
    },
    "token": "jwt-token-here"
}
```

**Status Codes**:
- 200 OK: Successfully logged in.
- 401 Unauthorized: Authentication failed.

### Get User Profile

**Endpoint**: `POST /api/v1/user/profile`

**Description**: Retrieve user profile.

**Request Body**:
```json
{
    "email": "john@example.com"
}
```

**Response**:
```json
{
    "message": "Data Retrived Sucess",
    "data": {
        "id": 1,
        "name": "John Doe",
        "email": "john@example.com"
    }
}
```

**Status Codes**:
- 200 OK: Successfully retrieved user profile.
- 500 Internal Server Error: General error occurred.

### Logout User

**Endpoint**: `POST /api/v1/user/logout`

**Description**: Logout a user.

**Request Headers**:
- Authorization: Bearer jwt-token-here

**Response**:
```json
{
    "message": "Successfully logged out"
}
```

**Status Codes**:
- 200 OK: Successfully logged out.
- 400 Bad Request: Token is missing.

## TaskController

### Create Task

**Endpoint**: `POST /api/v1/task/create`

**Description**: Create a new task.

**Request Body**:
```json
{
    "title": "New Task",
    "description": "Task description",
    "statusId": 1,
    "userId": 1
}
```

**Response**:
```json
{
    "message": "Task successfully created",
    "data": {
        "id": 1,
        "title": "New Task",
        "description": "Task description",
        "statusId": 1,
        "userId": 1
    }
}
```

**Status Codes**:
- 201 Created: Successfully created task.
- 400 Bad Request: UserId doesn't exist.
- 500 Internal Server Error: General error occurred.

### List Tasks by User ID

**Endpoint**: `POST /api/v1/task/tasks`

**Description**: List tasks for a specific user.

**Request Body**:
```json
{
    "id": 1
}
```

**Response**:
```json
{
    "message": "Data Retrieved Successfully",
    "data": [
        {
            "id": 1,
            "title": "Task 1",
            "description": "Description 1",
            "statusId": 1,
            "userId": 1
        }
    ]
}
```

**Status Codes**:
- 200 OK: Successfully retrieved tasks.
- 404 Not Found: Tasks not found for the user.
- 500 Internal Server Error: General error occurred.

### Update Task Status by ID

**Endpoint**: `POST /api/v1/task/update/status`

**Description**: Update task status by ID.

**Request Body**:
```json
{
    "id": 1,
    "statusId": 2
}
```

**Response**:
```json
{
    "message": "Data Updated SuccessFully",
    "data": {
        "id": 1,
        "title": "Task 1",
        "description": "Description 1",
        "statusId": 2,
        "userId": 1
    }
}
```

**Status Codes**:
- 200 OK: Successfully updated task status.
- 403 Forbidden: Update operation is not permitted.
- 500 Internal Server Error: General error occurred.

### Update Task

**Endpoint**: `PATCH /api/v1/task/update`

**Description**: Update a task.

**Request Body**:
```json
{
    "id": 1,
    "title": "Updated Task",
    "description": "Updated description",
    "statusId": 1,
    "userId": 1
}
```

**Response**:
```json
{
    "message": "Task updated SuccessFully",
    "data": {
        "id": 1,
        "title": "Updated Task",
        "description": "Updated description",
        "statusId": 1,
        "userId": 1
    }
}
```

**Status Codes**:
- 200 OK: Successfully updated task.
- 404 Not Found: Task not found.
- 500 Internal Server Error: General error occurred.

### Get Task by ID

**Endpoint**: `POST /api/v1/task/id`

**Description**: Retrieve task by ID.

**Request Body**:
```json
{
    "id": 1
}
```

**Response**:
```json
{
    "message": "Task retrieved Successfully",
    "data": {
        "id": 1,
        "title": "Task 1",
        "description": "Description 1",
        "statusId": 1,
        "userId": 1
    }
}
```

**Status Codes**:
- 200 OK: Successfully retrieved task.
- 404 Not Found: Task not found.
- 500 Internal Server Error: General error occurred.

### Delete Task

**Endpoint**: `DELETE /api/v1/task/delete`

**Description**: Delete a task.

**Request Body**:
```json
{
    "id": 1
}
```

**Response**:
```json
{
    "message": "Task successfully deleted",
    "data": 1
}
```

**Status Codes**:
- 200 OK: Successfully deleted task.
- 400 Bad Request: Invalid ID format.
- 404 Not Found: Task not found.
- 500 Internal Server Error: General error occurred.

### Get Task Counts by Status

**Endpoint**: `POST /api/v1/task/task-counts`

**Description**: Retrieve task counts by status for a specific user.

**Request Body**:
```json
{
    "userId": 1
}
```

**Response**:
```json
{
    "message": "Task count retrieved successfully",
    "data": {
        "todoCount": 5,
        "inProgressCount": 3,
        "doneCount": 2
    }
}
```

**Status Codes**:
- 200 OK: Successfully retrieved task counts.
- 400 Bad Request: Invalid user ID format.
- 404 Not Found: Task counts retrieval failed.
- 500 Internal Server Error: General error occurred.
