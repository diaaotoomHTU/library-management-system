function displayNewItemPopup() {
    document.getElementsByClassName("new-item-container")[0].style.display = "inline";
}

function hideNewItemPopup() {
    document.getElementsByClassName("new-item-container")[0].style.display = "none";
}

function displayEditItemPopup(id) {
    document.getElementsByClassName("edit-item-container")[0].style.display = "inline";
    document.getElementById("id-to-edit").value = id;
}

function hideEditItemPopup() {
    document.getElementsByClassName("edit-item-container")[0].style.display = "none";
}

function displayDeleteItemPopup(id) {
    document.getElementsByClassName("delete-item-container")[0].style.display = "inline";
    document.getElementById("id-to-delete").value = id;
}

function hideDeleteItemPopup() {
    document.getElementsByClassName("delete-item-container")[0].style.display = "none";
}

function displayLoanItemPopup(memberID, memberName) {
    document.getElementsByClassName("new-item-container")[0].style.display = "inline";
    document.getElementById("memberid-to-loan").value = memberID;
    document.getElementById("membername-to-loan").value = memberName;
}

function displayReturnItemPopup(bookID) {
    document.getElementsByClassName("delete-item-container")[0].style.display = "inline";
    document.getElementById("id-to-delete").value = bookID;
}

function calculateFine(borrowDate, returnDate, cost) {
    document.getElementById("fine-checkbox-input").setAttribute("onclick", "calculateFine('" + borrowDate + "', '" + returnDate + "', " + cost + ")");
    if (document.getElementById("fine-checkbox-input").checked == true) {
        document.getElementById("fine").textContent = "Fine: $" + cost;
    } else {
        var date1 = new Date(returnDate);
        var date2 = new Date();
        var diffTime = date2 - date1;
        var diffDays = Math.floor(diffTime / (1000 * 60 * 60 * 24));
        if (diffDays > 0) {
            document.getElementById("fine").textContent = "Fine: $" + (0.5 * diffDays);
        } else {
            document.getElementById("fine").textContent = "Fine: $0";
        }
    }


}

function hideErrorPopup() {
    document.getElementsByClassName("error-container")[0].style.display = "none";
}

function hideDateErrorPopup() {
    document.getElementsByClassName("date-error-container")[0].style.display = "none";
}




