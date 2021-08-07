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
            if(updateCategoryButton.classList.contains("display-none") && addCategoryButton.classList.contains("display-none")){
                addCategoryButton.classList.toggle("display-none");
            }
        }
    }

});

function resetColorPicker() {
    let radioForm = document.querySelector("#color-picker-input");
    console.log(radioForm);
    let radios = radioForm.elements["hat-color"];
    for (var i = 0, max = radios.length; i < max; i++) {
        radios[i].checked = false;
    }
}
function selectColorPicker(colour) {
    let radioForm = document.querySelector("#color-picker-input");
    let radios = radioForm.elements["hat-color"];
    for (var i = 0, max = radios.length; i < max; i++) {
        if(radios[i].value === colour){
            radios[i].checked = true;
        }
    }
}

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
    console.log(colour);
    console.log(name);
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
        .catch((error) => console.log(`Request failed ${error}`));
}

document.addEventListener('DOMContentLoaded', loadCategories, false);


function loadCategories() {
    fetch(`http://localhost:9092/category/all`)
        .then((response) => {
            if (response.status !== 200) {  //  2
                console.error(`status: ${reponse.status}`);
                return;
            }
            response.json() // 3
                .then(data => createCategories(data))
        }).catch((err) => console.error(`${err}`));
}

function updateCategoryElement(data){
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
    categoryEditIcon.onclick = () => {
        categoryNameInput.value = data.name;
        if(addColourLabel.classList.contains("display-none")){
            addColourLabel.classList.toggle("display-none");
            colourPicker.classList.toggle("display-none");
        }
        if(!addCategoryButton.classList.contains("display-none")){
            addCategoryButton.classList.toggle("display-none");
        }
        if(updateCategoryButton.classList.contains("display-none")){
            updateCategoryButton.classList.toggle("display-none");
        }
        selectColorPicker(data.colour);
        categoryId = data.id;
        categoryName = data.name;
        categoryColour = data.colour;
    }
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

function createCategories(data) {
    for (let i = 0; i < data.length; i++) {
        createCategoryElement(data[i]);
    }
}

function deleteCategoryElement(data){
    console.log(data);
    console.log(document.querySelector(`#category-item-${data}`));
    document.querySelector(`#category-item-${data}`).remove();
    categoryObservable.delete(`${data}`);
}

/***
 * Observer pattern
 */
const categoryObservable = new Map();

function Observable() 
{
    this.observers = [];
}

Observable.prototype = {
    subscribe: function(fn)
    {
        this.observers.push(fn);
    },
    unsubscribe: function(fnToRemove)
    {
        this.observers = this.observers.filter(fn => {
            if(fn != fnToRemove)
            return fn
        });
    },
    notify: function ()
    {
        this.observers.forEach ( fn => {
            fn.call();
        })
    },
    active: true
}

/**
 * Category filter
 */
let showAllCategoryButton = document.querySelector("#category-item-show-all");
showAllCategoryButton.onclick = () => {
    categoryObservable.forEach((key) => {
        if(key.active == false){
            key.active = true;
            key.notify();
        }
    });
}

function categoryFilterAllButOne(categoryId, categoryObservableMap) {
    categoryObservableMap.forEach((key, value) => {
        if(value == categoryId){
            if(key.active == false){
                key.active = true;
                key.notify();
            }
        } else {
            if(key.active == true){
                key.active = false;
                key.notify();
            }
        }
    });
}


/**
 * Dashboard - Tasks
 */

fetch(`http://localhost:9092/category/all`)
    .then((response) => {
        if (response.status !== 200) {  //  2
            console.error(`status: ${reponse.status}`);
            return;
        }
        response.json() // 3
        .then(data => createTasksForCategory(data))
    }).catch((err)=> console.error(`${err}`)); 



const getTasksByCategory = (categoryData) => { 
    fetch(`http://localhost:9092/task/${categoryData.id}/getallbycategory`) // 1
        .then((response) => {
            if (response.status !== 200) {  //  2
                console.error(`status: ${reponse.status}`);
                return;
            }
            response.json() // 3
            .then(taskData => createTaskCards(taskData, categoryData))
        }).catch((err)=> console.error(`${err}`)); 
    }

function createTasksForCategory(data) {
    for(let i = 0; i < data.length; i++){
        getTasksByCategory(data[i])
        const observable = new Observable();
        categoryObservable.set(`${data[i].id}`, observable);

    }
}

    function createTaskCards(taskData, categoryData){
        let cardGroup = document.querySelector("#card-group");
        for(let i = 0; i < taskData.length; i ++){

            let card = document.createElement("div");
            card.setAttribute("id", `card${taskData[i].id}`);
            card.setAttribute("class", `card mb-3 category-${categoryData.id}`);
            
            let cardHeader = document.createElement("div");
            cardHeader.setAttribute("class", "card-header d-flex justify-content-between");
            cardHeader.setAttribute("style", `background-color: ${categoryData.colour};`)
            cardHeader.textContent = categoryData.name;
            card.appendChild(cardHeader);

            let cardEditIcon = document.createElement("i");
            cardEditIcon.setAttribute("class", "card-icon bx bxs-edit");
            cardHeader.appendChild(cardEditIcon);

            let cardBody = document.createElement("div");
            cardBody.setAttribute("class", "card-body");
            card.appendChild(cardBody);

            let cardBodyHeader = document.createElement("h5");
            cardBodyHeader.setAttribute("class", "card-title");
            cardBodyHeader.textContent = taskData[i].title;
            cardBody.appendChild(cardBodyHeader);

            let cardBodyDescription = document.createElement("p");
            cardBodyDescription.setAttribute("class", "card-text");
            cardBodyDescription.textContent = taskData[i].description;
            cardBody.appendChild(cardBodyDescription);

            let cardFooter = document.createElement("div");
            cardFooter.setAttribute("class", "card-footer d-flex justify-content-between bg-transparent");
            cardFooter.textContent = taskData[i].dueDate;
            card.appendChild(cardFooter);

            let cardPriority = document.createElement("span");
            cardPriority.setAttribute("class", `priority priority-${taskData[i].priority.toLowerCase()}`);
            cardPriority.textContent = taskData[i].priority;
            cardFooter.appendChild(cardPriority);

            let cardDeleteIcon = document.createElement("i");
            cardDeleteIcon.setAttribute("class", "card-icon bx bx-trash");
            cardFooter.appendChild(cardDeleteIcon);

            function hideElement() {
                card.classList.toggle("display-none");
            }

            categoryObservable.get(`${categoryData.id}`).subscribe(hideElement);

            cardGroup.appendChild(card);
        }
        
    }

    



