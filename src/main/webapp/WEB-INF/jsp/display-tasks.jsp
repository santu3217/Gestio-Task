<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>
    <style> 
        body {
            margin-top: 70px;
            font-family: Arial, sans-serif;
        }
        .heading {
            padding-left: 180px;
        }
        .all-tasks {
            display: flex;
            justify-content: space-between;  /* Distribute columns with equal space  */
            max-width: 1200px; /* Set maximum width for the container */
            width: 100%;
            padding: 20px; /* Add padding to create margins */
            margin-left: 150px;
        }
        .column {
            float: left;
            width: 30%;
            padding: 10px;
            min-height: 100vh;
            white-space: nowrap;   /* Prevent text wrapping */ 
            overflow: visible;   /* Enable horizontal scrolling for overflow */ 
        }
        
        .all-tasks:first-child() {
            margin-left: 50px;
        }

        .task {
            border: 1px solid #ddd;
            padding: 10px;
            width: 250px;
            margin-bottom: 10px;
            cursor: pointer; /* Make tasks clickable */
            display: block; /* Ensure tasks are displayed inline */
        }
        .task.ui-draggable-dragging {
            z-index: 9999; /* Set a high z-index for the dragged task */
        }
        .column h3 {
            margin-top: 0;
        }
        /* Modal styles */
        .modal {
            display: none;
            position: fixed;
            z-index: 9999;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0, 0, 0, 0.5);
            
        }
        .modal-content {
            background-color: #fefefe;
            margin: 15% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 45%;
        }
        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }
        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }
        .emptyDashboard {
            text-align: center;
            position: absolute; /* Position it absolutely */
            top: 50%; /* Move it 50% from the top */
            left: 50%; /* Move it 50% from the left */
            transform: translate(-50%, -50%); /* Center it both horizontally and vertically */
        }
    </style>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
    <script>
        $(document).ready(function () {
            // Function to calculate and set the height of the columns
            var currentOrgId = $("#orgId").val();
            var currentTeamId = $("#teamId").val();
                
            function setColumnHeight() {
                $(".column").each(function() {
                    var column = $(this);
                    var tallestTaskHeight = 0;
                    column.find(".task").each(function() {
                        var taskHeight = $(this).outerHeight(true);
                        if (taskHeight > tallestTaskHeight) {
                            tallestTaskHeight = taskHeight;
                        }
                    });
                    // Set the height of the column to the tallest task height
                    column.css("height", tallestTaskHeight + 1000);
                });
            }

            // Call the function initially and on window resize
            setColumnHeight();
            $(window).resize(setColumnHeight);
            console.log("EXECUTED");
            // Color code tasks based on the column
            $("#Pending .task").css("background-color", "red");
            $("#InProgress .task").css("background-color", "orange");
            $("#Completed .task").css("background-color", "green");

            $(".task").draggable({
                revert: true,
                cursor: "move",
                start: function(event, ui) {
                    $(this).data("originalColumnId", $(this).parent().attr("id"));
                }
            });

            $(".task").click(function() {
                var task = $(this);
                var taskId = task.attr("id").split("-")[1];
                // Populate the task model with details
                $("#taskModal .name").text("Name: " + task.find("h4").text());
                $("#taskModal .description").text("Description: " + task.find(".description").text());
                $("#taskModal .status").text(task.find("p:eq(0)").text());
                $("#taskModal .assignTo").text(task.find("p:eq(1)").text());
                $("#taskModal .deadline").text(task.find("p:eq(2)").text());
                $("#taskModal").data("taskId", taskId); // Store task ID in modal data
      
                // Show the modal
                $("#taskModal").show();
            });

            $(".close").click(function() {
                // Close the modal when the close button is clicked
                $("#taskModal").hide();
            });
            
            // Delete functionality inside task modal
            $("#taskModal .delete").click(function() {
                var taskId = $("#taskModal").data("taskId");
                $.ajax({
                    url: "/FinalProject/dashboard/org-" + currentOrgId + "/t/" + currentTeamId + "}/tasks/delete/" + taskId,
                    type: "DELETE",
                    success: function (response) {
                        // Remove the task element from the DOM
                        $("#task-" + taskId).remove();
                        // Close the modal
                        $("#taskModal").hide();
                        // Optionally, you can update the column height after deleting the task
                        setColumnHeight();
                    },
                    error: function (xhr, status, error) {
                        console.log("Error deleting task:", error);
                    }
                });
            });
            
            $("#taskModal .update").click(function() {
                var taskId = $("#taskModal").data("taskId");
                window.location.href = "./tasks/update/" + taskId;
            });
                       
            $(".column").droppable({
                accept: ".task",
                drop: function (event, ui) {
                    var column = $(this);
                    var task = ui.draggable;
                    var originalColumnId = task.data("originalColumnId");
                    var newColumnId = column.attr("id");
                    task.find("p:eq(0)").text("Status: " + newColumnId);
                    if (originalColumnId !== newColumnId) {
                        var taskId = task.attr("id").split("-")[1];
                        $.ajax({
                            url: "/FinalProject/dashboard/org-" + currentOrgId + "/t/" + currentTeamId+ "/tasks/update/status/" + taskId + "?status=" + newColumnId,
                            type: "PUT",
                            data: {},          
                            success: function (response) {
                                console.log("SUCCESS CLUMN");
                                task.data("originalColumnId", newColumnId);
                                column.append(task);
                                // Change task color based on the column
                                if (newColumnId === "Pending") {
                                    task.css("background-color", "red");
                                } else if (newColumnId === "InProgress") {
                                    task.css("background-color", "orange");
                                } else if (newColumnId === "Completed") {
                                    task.css("background-color", "green");
                                }
                                // Update column height after dropping the task
                                setColumnHeight();
                            },
                            error: function (xhr, status, error) {
                                console.error("Error updating task status:", error);
                                column.append(task);
                                if (newColumnId === "Pending") {
                                    task.css("background-color", "red");
                                } else if (newColumnId === "InProgress") {
                                    task.css("background-color", "orange");
                                } else if (newColumnId === "Completed") {
                                    task.css("background-color", "green");
                                }
                                // Update column height after dropping the task
                                setColumnHeight();
                            }
                        });
                    }
                }
            });
        });
        
    </script>

</head>
<body>
    <h1 class="heading">${dashboardName}</h1>
    <c:choose>
        <c:when test="${not empty createdTasks || not empty inProgressTasks || not empty completedTasks}" >
            <div class="all-tasks">
                <div class="column" id="Pending"> 
                    <h3>Pending</h3>
                    <c:forEach var="task" items="${createdTasks}">
                        <div class="task" id="task-${task.id}">
                            <h4>${task.name}</h4>
                            <p>Status: ${task.status}</p>
                            <p>Assigned to: ${task.assignTo.email}</p>
                            <p>Deadline: ${task.deadline}</p>
                        </div>
                    </c:forEach>
                </div>

                <div class="column" id="InProgress">
                    <h3>In Progress</h3>
                    <c:forEach var="task" items="${inProgressTasks}">
                        <div class="task" id="task-${task.id}">
                            <h4>${task.name}</h4>
                            <p>Status: ${task.status}</p>
                            <p>Assigned to: ${task.assignTo.email}</p>
                            <p>Deadline: ${task.deadline}</p>
                        </div>
                    </c:forEach>
                </div>

                <div class="column" id="Completed">
                    <h3>Completed</h3>
                    <c:forEach var="task" items="${completedTasks}">
                        <div class="task" id="task-${task.id}">
                           <h4>${task.name}</h4>
                            <p>Status: ${task.status}</p>
                            <p>Assigned to: ${task.assignTo.email}</p>
                            <p>Deadline: ${task.deadline}</p>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="emptyDashboard">
                <c:set var="userData" value="${sessionScope.user}" />
                <h1>No Tasks to Display</h1>
                <a href="${pageContext.request.contextPath}/dashboard/org-${userData.getOrg().getId()}/t/${userData.getTeam().getId()}/tasks/create">Create Task</a>
            </div>
        </c:otherwise>
    </c:choose>
    
    <!-- Modal for task details -->
    <div id="taskModal" class="modal">
        <div class="modal-content">
            <span class="close">&times;</span>
            <!-- Task details will go here -->
            <h1>Task Details</h3>
            <h4 class="name"></h4>
            <h4 class="status"></h4>
            <h4 class="assignTo"></h4>
            <h4 class="deadline"></h4>
            <button type="button" class="delete">Delete</button>
            <button type="button" class="update">Update</button>
        </div>
    </div>
    
    <form style="display: none;">
        <input type="hidden" id="orgId" value="${sessionScope.user.getOrg().getId()}">
        <input type="hidden" id="teamId" value="${sessionScope.user.getTeam().getId()}">
    </form>
</body>
</html>
