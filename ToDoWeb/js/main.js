/**
 * Sidebar
 */
let sidebar = document.querySelector(".sidebar");
let closeBtn = document.querySelector("#btn");
let closeBtnDiv = document.querySelector("#menu-btn-div");

let categoriesBtn = document.querySelector(".bx-category");
let categoriesDiv = document.querySelector("#categories");
let addTaskBtn = document.querySelector(".bx-list-plus");
let addTaskDiv = document.querySelector("#add-task");

let categoriesBtnIcon = document.querySelector("#categories-icon");
let addTaskBtnIcon = document.querySelector("#task-icon");
let listCategoriesDiv = document.querySelector("#list-categories");
let addTaskFormDiv = document.querySelector("#add-task-form");

closeBtnDiv.addEventListener("click", () => {
    if (closeBtn.classList.contains("bx-menu") ||
        closeBtn.classList.contains("bx-menu-alt-right")) {
        sidebar.classList.toggle("open");
        menuBtnChange();//calling the function(optional)
    } 
});

categoriesDiv.addEventListener("click", () => {
    displayFilter("category");
});

addTaskDiv.addEventListener("click", () => {
    displayFilter("addTask");
});

// following are the code to change sidebar button(optional)
function menuBtnChange() {
    if (sidebar.classList.contains("open")) {
        closeBtn.classList.replace("bx-menu", "bx-menu-alt-right");//replacing the iocns class
    } else {
        closeBtn.classList.replace("bx-menu-alt-right", "bx-menu");//replacing the iocns class
    }
}



/**
 * Categories
 */

let addCategoryDiv = document.querySelector("#add-category");
let categoryNameInput = document.querySelector("#category-name");
let colourPicker = document.querySelector("#colour-picker");
let colourPickerInput = document.querySelector("#colour-picker-input");
let categoryLabel = document.querySelector("#add-category-label");
let addColourLabel = document.querySelector("#add-colour-label");
let addCategoryButton = document.querySelector("#submit-category");
let updateCategoryButton = document.querySelector("#update-category");
let categoryToMenuButton = document.querySelector("#categories-menu");
let categoryName;
let categoryColour;
let categoryId;

categoryNameInput.addEventListener("keyup", function (event) {
    categoryName = categoryNameInput.value;
    event.preventDefault();
    if (colourPicker.classList.contains("display-none")) {
        colourPicker.classList.toggle("display-none");
        addColourLabel.classList.toggle("display-none");
    }
});


function waitForElement(selector) {
    return new Promise(function (resolve, reject) {
        var element = document.querySelector(selector);
        if (element) {
            resolve(element);
            return;
        }

        var observer = new MutationObserver(function (mutations) {
            mutations.forEach(function (mutation) {
                var nodes = Array.from(mutation.addedNodes);
                for (var node of nodes) {
                    if (node.matches && node.matches(selector)) {
                        observer.disconnect();
                        resolve(node);
                        return;
                    }
                };
            });
        });
        observer.observe(document.documentElement, { childList: true, subtree: true });
    })
}

waitForElement("#color-picker-input").then(() => {
    let radioForm = document.querySelector("#color-picker-input");
    let radios = radioForm.elements["hat-color"];
    for (var i = 0, max = radios.length; i < max; i++) {
        radios[i].onclick = function () {
            categoryColour = this.value;
            if (updateCategoryButton.classList.contains("display-none") && addCategoryButton.classList.contains("display-none")) {
                addCategoryButton.classList.toggle("display-none");
            }
        }
    }

});

function resetColorPicker() {
    let radioForm = document.querySelector("#color-picker-input");
    let radios = radioForm.elements["hat-color"];
    for (var i = 0, max = radios.length; i < max; i++) {
        radios[i].checked = false;
    }
}
function selectColorPicker(colour) {
    let radioForm = document.querySelector("#color-picker-input");
    let radios = radioForm.elements["hat-color"];
    for (var i = 0, max = radios.length; i < max; i++) {
        if (radios[i].value === colour) {
            radios[i].checked = true;
        }
    }
}

categoryToMenuButton.onclick = (() => {
    displayFilter("menu");
});

updateCategoryButton.onclick = () => {
    updateCategory(categoryName, categoryColour, categoryId);
    colourPicker.classList.toggle("display-none");
    addColourLabel.classList.toggle("display-none");
    updateCategoryButton.classList.toggle("display-none");
    categoryNameInput.value = "";
    resetColorPicker();
}
addCategoryButton.onclick = () => {
    createCategory(categoryName, categoryColour);
    colourPicker.classList.toggle("display-none");
    addColourLabel.classList.toggle("display-none");
    addCategoryButton.classList.toggle("display-none");
    categoryNameInput.value = "";
    resetColorPicker();
}


const deleteCategory = (id) => {
    fetch(`http://localhost:9092/category/${id}`, {
        method: 'delete'  //3
    })
        .then(res => res.json())
        .then((data) => deleteCategoryElement(data))
        .catch((error) => {
            console.log(`Request failed ${error}`);
        });
}

const createCategory = (name, colour) => {
    fetch("http://localhost:9092/category", { //1
        method: 'post', //2
        headers: {
            "Content-type": "application/json" //3
        },
        body: JSON.stringify( //4
            {
                "name": name,//5
                "colour": colour
            }
        )
    })
        .then(res => res.json())
        .then((data) => createCategoryElement(data))
        .catch((error) => console.log(`Request failed ${error}`));
}

const updateCategory = (name, colour, id) => {
    fetch(`http://localhost:9092/category/${id}`, { //1
        method: 'put', //2
        headers: {
            "Content-type": "application/json" //3
        },
        body: JSON.stringify( //4
            {
                "name": name,//5
                "colour": colour
            }
        )
    })
        .then(res => res.json())
        .then((data) => updateCategoryElement(data))
        .then((data) => updateCategoryHeadersOnCards(data))
        .catch((error) => console.log(`Request failed ${error}`));
}


fetch(`http://localhost:9092/category/all`)
    .then((response) => {
        if (response.status !== 200) {  //  2
            console.error(`status: ${reponse.status}`);
            return;
        }
        response.json() // 3
            .then(data => createCategories(data))
    }).catch((err) => console.error(`${err}`));


function createCategories(data) {

    for (let i = 0; i < data.length; i++) {
        createCategoryElement(data[i]);
        populateCategoryAddTaskDropdown(data[i]);
    }
}
function populateCategoryAddTaskDropdown(data) {
    let categorySelect = document.querySelector("#task-category");
    let optionItem = document.createElement("option");
    optionItem.setAttribute("id", `task-category-item-${data.id}`);
    optionItem.value = JSON.stringify(data);
    optionItem.textContent = data.name;
    optionItem.setAttribute("style", `background: ${data.colour}`);
    categorySelect.appendChild(optionItem);
}

const updateCategoryHeadersOnCards = (category) => {
    console.log("inhere");
    console.log(JSON.stringify(category));
    document.querySelectorAll(`.category-header-${category.id}`).forEach(
        e => e.setAttribute("style", `background: ${category.colour}`));
    document.querySelectorAll(`.category-header-${category.id}`).forEach(
        e => e.firstChild.textContent = category.name);
}


function updateCategoryElement(data) {
    let categoryElement = document.querySelector(`#category-item-${data.id}`);
    let categoryElements = categoryElement.childNodes;
    let categoryAnchor = categoryElements[0];
    categoryAnchor.setAttribute("style", `background: linear-gradient(110deg, #11101D 70%, ${data.colour} 30%)`);
    categoryAnchor.onmouseover = function () {
        categoryAnchor.setAttribute("style", `background: linear-gradient(110deg, #FFF 70%, ${data.colour} 30%)`);
    }
    categoryAnchor.onmouseleave = function () {
        categoryAnchor.setAttribute("style", `background: linear-gradient(110deg, #11101D 70%, ${data.colour} 30%)`);
    }
    categoryAnchorElements = categoryAnchor.childNodes;
    categorySpan = categoryAnchorElements[0];
    categorySpan.textContent = data.name;
    categoryIconDiv = categoryAnchorElements[1];
    categoryIconDivNodes = categoryIconDiv.childNodes;
    categoryEditIcon = categoryIconDivNodes[0];
    categoryEditIcon.onclick = () => editIconClick(data);
    let categoryListItem = document.querySelector(`#task-category-item-${data.id}`);
    categoryListItem.textContent = data.name;
    categoryListItem.setAttribute("style", `background: ${data.colour}`);

    return data;
}

function createCategoryElement(data) {
    let categoriesList = document.querySelector("#list-categories");

    let categoryItem = document.createElement("li");
    categoryItem.setAttribute("id", `category-item-${data.id}`);
    categoryItem.setAttribute("class", "category-item");
    categoriesList.appendChild(categoryItem);

    let categoryAnchor = document.createElement("a");
    categoryAnchor.setAttribute("style", `background: linear-gradient(110deg, #11101D 70%, ${data.colour} 30%)`);
    categoryAnchor.onclick = function () {
        categoryFilterAllButOne(data.id, categoryObservable);
    }
    categoryAnchor.onmouseover = function () {
        categoryAnchor.setAttribute("style", `background: linear-gradient(110deg, #FFF 70%, ${data.colour} 30%)`);
    }
    categoryAnchor.onmouseleave = function () {
        categoryAnchor.setAttribute("style", `background: linear-gradient(110deg, #11101D 70%, ${data.colour} 30%)`);
    }
    categoryItem.appendChild(categoryAnchor);

    let categorySpan = document.createElement("span");
    categorySpan.setAttribute("class", "links_name");
    categorySpan.textContent = data.name;
    categoryAnchor.appendChild(categorySpan);

    let iconDiv = document.createElement("div");
    categoryAnchor.appendChild(iconDiv);

    let categoryEditIcon = document.createElement("i");
    categoryEditIcon.setAttribute("class", "bx bxs-edit");
    categoryEditIcon.onclick = () => editIconClick(data);
    iconDiv.appendChild(categoryEditIcon);


    let categoryDeleteIcon = document.createElement("i");
    categoryDeleteIcon.setAttribute("class", "bx bx-trash");
    categoryDeleteIcon.onclick = () => {
        deleteCategory(data.id);
    }
    iconDiv.appendChild(categoryDeleteIcon);

    let categorySpanTooltip = document.createElement("span");
    categorySpanTooltip.setAttribute("class", "tooltip");
    categorySpanTooltip.textContent = data.name;
    categoryAnchor.appendChild(categorySpanTooltip);
}

function deleteCategoryElement(data) {
    console.log("daaa " + data);
    console.log("in here")
    document.querySelectorAll(`.category-${data}`).forEach(e => e.remove());
    document.querySelector(`#category-item-${data}`).remove();
    categoryObservable.delete(`${data}`);
    showAllCategories();
}

const editIconClick = (data) => {
    categoryNameInput.value = data.name;
    if (addColourLabel.classList.contains("display-none")) {
        addColourLabel.classList.toggle("display-none");
        colourPicker.classList.toggle("display-none");
    }
    if (!addCategoryButton.classList.contains("display-none")) {
        addCategoryButton.classList.toggle("display-none");
    }
    if (updateCategoryButton.classList.contains("display-none")) {
        updateCategoryButton.classList.toggle("display-none");
    }
    selectColorPicker(data.colour);
    categoryId = data.id;
    categoryName = data.name;
    categoryColour = data.colour;
}

/***
 * Observer pattern
 */
const categoryObservable = new Map();
const sidebarDisplayObservable = new Map();

function Observable() {
    this.observers = [];
}

Observable.prototype = {
    subscribe: function (fn) {
        this.observers.push(fn);
    },
    unsubscribe: function (fnToRemove) {
        this.observers = this.observers.filter(fn => {
            if (fn != fnToRemove)
                return fn
        });
    },
    notify: function () {
        this.observers.forEach(fn => {
            fn.call();
        })
    },
    active: true
}

/**
 * Category filter
 */
function showAllCategories() {
    categoryObservable.forEach((key) => {
        if (key.active == false) {
            key.active = true;
            key.notify();
        }
    });
}
let showAllCategoryButton = document.querySelector("#category-item-show-all");
showAllCategoryButton.onclick = () => {
    showAllCategories();
}

function categoryFilterAllButOne(categoryId, categoryObservableMap) {
    categoryObservableMap.forEach((key, value) => {
        if (value == categoryId) {
            if (key.active == false) {
                key.active = true;
                key.notify();
            }
        } else {
            if (key.active == true) {
                key.active = false;
                key.notify();
            }
        }
    });
}


/**
 * Tasks
 */
waitForElement("#task-menu").then(() => {
    let taskToMenuButton = document.querySelector("#task-menu");
    taskToMenuButton.onclick = (() => {
        displayFilter("menu");
        addTaskFormDefault();
    });
});

fetch(`http://localhost:9092/category/all`)
    .then((response) => {
        if (response.status !== 200) {  //  2
            console.error(`status: ${reponse.status}`);
            return;
        }
        response.json() // 3
            .then(data => createTasksForCategory(data))
    }).catch((err) => console.error(`${err}`));

const createTaskPost = (categoryData, taskData) => {
    fetch(`http://localhost:9092/task/${categoryData.id}/create`, { //1
        method: 'post', //2
        headers: {
            "Content-type": "application/json" //3
        },
        body: JSON.stringify(taskData)
    })
        .then(res => res.json())
        .then((dataBack) => createTaskCard(dataBack, categoryData)
        )
        .catch((error) => console.log(`Request failed ${error}`));
}

const updateTaskPost = (category, taskId, task) => {
    console.log(category.id);
    console.log(taskId);
    console.log(JSON.stringify(task));
    fetch(`http://localhost:9092/task/${category.id}/${taskId}/update`, { //1
        method: 'put', //2
        headers: {
            "Content-type": "application/json" //3
        },
        body: JSON.stringify(task)
    })
        .then(res => res.json())
        .then((data) => updateTaskElement(data))
        .catch((error) => console.log(`Request failed ${error}`));
}

const deleteTaskFetch = (id) => {
    fetch(`http://localhost:9092/task/${id}`, {
        method: 'delete'  //3
    })
        .then(res => res.json())
        .then((data) => deleteTaskElement(data))
        .catch((error) => {
            console.log(`Request failed ${error}`);
        });

}

const getCategoryById = (id) => {
    console.log("in hereee");
    fetch(`http://localhost:9092/category/${id}`)
        .then((response) => {
            if (response.status !== 200) {  //  2
                console.error(`status: ${reponse.status}`);
                return;
            }
            return response.json() // 3
                .then(data => console.log(data));
        }).catch((err) => console.error(`${err}`));
}


const getTasksByCategory = (categoryData) => {
    fetch(`http://localhost:9092/task/${categoryData.id}/getallbycategory`) // 1
        .then((response) => {
            if (response.status !== 200) {  //  2
                console.error(`status: ${reponse.status}`);
                return;
            }
            response.json() // 3
                .then(taskData => createTaskCards(taskData, categoryData))
        }).catch((err) => console.error(`${err}`));
}



waitForElement("#task-form").then(() => {
    let submitTaskButton = document.querySelector("#submit-task");
    submitTaskButton.onclick = () => {
        let errorCheck = true;
        let createTask = {
            "title": document.querySelector('#task-name').value,
            "description": document.querySelector('#task-description').value,
            "dueDate": document.querySelector('#task-date').value,
            "priority": document.querySelector('#task-priority').value
        };
        Object.values(createTask).forEach(value => {
            if (value.length === 0) {
                errorCheck = false;
            }
        });
        let categoryJSON = document.querySelector("#task-category").value;
        let categoryObj;
        if (categoryJSON.length === 0) {
            errorCheck = false;
        }
        if (errorCheck === true) {
            categoryObj = JSON.parse(categoryJSON);
            console.log("in here")
            createTaskPost(categoryObj, createTask);
            addTaskFormDefault();
        } else {
            document.querySelector("#task-error").classList.toggle("display-none");
        }
    }
});


function createTasksForCategory(data) {
    for (let i = 0; i < data.length; i++) {
        getTasksByCategory(data[i])
        const observable = new Observable();
        categoryObservable.set(`${data[i].id}`, observable);

    }
}

function createTaskCards(tasksData, categoryData) {
    for (let i = 0; i < tasksData.length; i++) {
        createTaskCard(tasksData[i], categoryData);
    }
}

function createTaskCard(taskData, categoryData) {
    let cardGroup = document.querySelector("#card-group");

    let card = document.createElement("div");
    card.setAttribute("id", `card${taskData.id}`);
    card.setAttribute("class", `card mb-3 category-${categoryData.id}`);

    let cardHeader = document.createElement("div");
    cardHeader.setAttribute("class", `card-header d-flex justify-content-between category-header-${categoryData.id}`);
    cardHeader.setAttribute("style", `background-color: ${categoryData.colour};`)
    cardHeader.textContent = categoryData.name;
    card.appendChild(cardHeader);

    let cardEditIcon = document.createElement("i");
    cardEditIcon.setAttribute("class", "card-icon bx bxs-edit");
    cardEditIcon.onclick = () => {
        editTaskIcon(taskData, categoryData);
    }
    cardHeader.appendChild(cardEditIcon);

    let cardBody = document.createElement("div");
    cardBody.setAttribute("class", "card-body");
    card.appendChild(cardBody);

    let cardBodyHeader = document.createElement("h5");
    cardBodyHeader.setAttribute("class", "card-title");
    cardBodyHeader.textContent = taskData.title;
    cardBody.appendChild(cardBodyHeader);

    let cardBodyDescription = document.createElement("p");
    cardBodyDescription.setAttribute("class", "card-text");
    cardBodyDescription.textContent = taskData.description;
    cardBody.appendChild(cardBodyDescription);

    let cardFooter = document.createElement("div");
    cardFooter.setAttribute("class", "card-footer d-flex justify-content-between bg-transparent");
    cardFooter.textContent = taskData.dueDate;
    card.appendChild(cardFooter);

    let cardPriority = document.createElement("span");
    cardPriority.setAttribute("class", `priority priority-${taskData.priority.toLowerCase()}`);
    cardPriority.textContent = taskData.priority;
    cardFooter.appendChild(cardPriority);

    let cardDeleteIcon = document.createElement("i");
    cardDeleteIcon.setAttribute("class", "card-icon bx bx-trash");
    cardDeleteIcon.onclick = () => {
        deleteTaskFetch(taskData.id);
    }
    cardFooter.appendChild(cardDeleteIcon);

    function hideElement() {
        card.classList.toggle("display-none");
    }

    categoryObservable.get(`${categoryData.id}`).subscribe(hideElement);

    cardGroup.appendChild(card);
}

function updateTaskElement() {

}

function editTaskIcon(taskData, categoryData) {
    displayFilter("updateTask");
    document.querySelector('#task-name').value = taskData.title;
    document.querySelector('#task-description').value = taskData.description;
    document.querySelector('#task-date').value = taskData.dueDate;
    document.querySelector('#task-priority').value = taskData.priority;
    document.querySelector('#task-category').value = JSON.stringify(categoryData);

    let updateTaskButton = document.querySelector("#submit-task-update");
    updateTaskButton.onclick = () => {
        let errorCheck = true;
        let updatedTask = {
            "title": document.querySelector('#task-name').value,
            "description": document.querySelector('#task-description').value,
            "dueDate": document.querySelector('#task-date').value,
            "priority": document.querySelector('#task-priority').value
        };
        Object.values(updatedTask).forEach(value => {
            if (value.length === 0) {
                errorCheck = false;
            }
        });
        let categoryJSON = document.querySelector("#task-category").value;
        let categoryObj;
        if (categoryJSON.length === 0) {
            errorCheck = false;
        }
        if (errorCheck === true) {
            categoryObj = JSON.parse(categoryJSON);
            console.log("in here")
            updateTaskPost(categoryObj, taskData.id, updatedTask);
            addTaskFormDefault();
        } else {
            document.querySelector("#task-error").classList.toggle("display-none");
        }
    }
}

function deleteTaskElement(data) {
    console.log(data);
    console.log(document.querySelector(`#card${data}`));
    document.querySelector(`#card${data}`).remove();
}

function addTaskFormDefault() {
    document.querySelector('#task-name').value = "";
    document.querySelector('#task-description').value = "";
    document.querySelector('#task-date').value = "";
    document.querySelector('#task-priority').value = "";
    document.querySelector('#task-category').value = "";
}
/**
 * Helper
 */
function toggleDisplay(id) {
    let thisElement = document.querySelector(`#${id}`);
    thisElement.classList.toggle("display-none");
}
/**
 * Update task display observable
 */
const updateTaskDisplay = new Observable();

updateTaskDisplay.active = false;

updateTaskDisplay.subscribe(() => {
    if (!sidebar.classList.contains("open")) {
        sidebar.classList.toggle("open");
    }
})

updateTaskDisplay.subscribe(() => {
    toggleDisplay("add-task-form");
})

updateTaskDisplay.subscribe(() => {
    toggleDisplay("submit-task-update")
})

sidebarDisplayObservable.set("updateTask", updateTaskDisplay);

/**
 * Menu items display observable
 */
const menuDisplay = new Observable();

menuDisplay.active = true;

menuDisplay.subscribe(() => {
    toggleDisplay("menu-btn-div");
})

menuDisplay.subscribe(() => {
    toggleDisplay("categories");
})

menuDisplay.subscribe(() => {
    toggleDisplay("add-task");
})

sidebarDisplayObservable.set("menu", menuDisplay);

/**
 * Add task display observable
 */
const addTaskDisplay = new Observable();
addTaskDisplay.active = false;

addTaskDisplay.subscribe(() => {
    if (!sidebar.classList.contains("open")) {
        sidebar.classList.toggle("open");
    }
})

addTaskDisplay.subscribe(() => {
    toggleDisplay("add-task-form");
})

addTaskDisplay.subscribe(() => {
    toggleDisplay("submit-task")
})

sidebarDisplayObservable.set("addTask", addTaskDisplay);
/**
 * Category display observable
 */

const categoryDisplay = new Observable();
categoryDisplay.active = false;

categoryDisplay.subscribe(() => {
    toggleDisplay("list-categories")
})

categoryDisplay.subscribe(() => {
    if (!sidebar.classList.contains("open")) {
        sidebar.classList.toggle("open");
    }
})

sidebarDisplayObservable.set("category", categoryDisplay);

function displayFilter(displayName) {
    sidebarDisplayObservable.forEach((key, value) => {
        if (value == displayName) {
            if (key.active == false) {
                key.active = true;
                key.notify();
            }
        } else {
            if (key.active == true) {
                key.active = false;
                key.notify();
            }
        }
    });
}










