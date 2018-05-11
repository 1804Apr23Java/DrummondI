let submitForm = document.getElementById("submitForm");
submitForm.addEventListener("click", function(){
    let p = document.createElement('P');
    p.innerHTML = document.getElementById("inputName").value+' : ' +document.getElementById("commentBox").value;
    document.getElementById("outputForm").appendChild(p);
});
