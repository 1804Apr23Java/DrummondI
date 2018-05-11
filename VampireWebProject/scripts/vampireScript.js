let submitForm = document.getElementById("submitForm");
submitForm.addEventListener("click", function(){
    let p = document.createElement('P');
    let h = document.createElement('h3');
    
    p.innerHTML = document.getElementById("inputName").value+' : ' +document.getElementById("commentBox").value;
    h.innerHTML = "All Comments";

    document.body.appendChild(h);
    document.body.appendChild(p);
});
