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
    }
}



{/* <div class="card mb-3">
                    <div class="card-header d-flex justify-content-between" style="background-color: aqua;">
                        Header 
                        <i class='card-icon bx bxs-edit'></i>
                    </div>
                </div> */}

    function createTaskCards(taskData, categoryData){
        let cardGroup = document.querySelector("#card-group");
        for(let i = 0; i < taskData.length; i ++){

            let card = document.createElement("div");
            card.setAttribute("id", `card${categoryData.id}-${taskData.id}`);
            card.setAttribute("class", `card mb-3 category-${categoryData.id}`);
            
            let cardHeader = document.createElement("div");
            cardHeader.setAttribute("class", "card-header d-flex justify-content-between");
            cardHeader.setAttribute("style", `background-color: ${categoryData.colour};`)
            cardHeader.textContent = categoryData.name;
            card.appendChild(cardHeader);

            let cardEditIcon = document.createElement("i");
            cardEditIcon.setAttribute("class", "card-icon bx bxs-edit");
            cardHeader.appendChild(cardEditIcon);

            // <div class="card-body">
            //         <h5 class="card-title">Success card title</h5>
            //         <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
            //         </div>
            //         <div class="card-footer d-flex justify-content-between bg-transparent">
            //             Footer
            //             <i class='card-icon bx bx-trash'></i>
            //         </div>
            let cardBody = document.createElement("div");
            cardBody.setAttribute("class", "card-body");
            card.appendChild(cardBody);

            let cardPriority = document.createElement("span");
            cardPriority.setAttribute("style", ``)

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

            let cardDeleteIcon = document.createElement("i");
            cardDeleteIcon.setAttribute("class", "card-icon bx bx-trash");
            cardFooter.appendChild(cardDeleteIcon);
            
            cardGroup.appendChild(card);
        }
        
    }

    function backgroundColourForPriority(data){
        switch(data){
            case "low": 
            return yellow;
            case "medium": 
            return green;
            case "high": 
            return red;
            default: yellow;
        }
    }