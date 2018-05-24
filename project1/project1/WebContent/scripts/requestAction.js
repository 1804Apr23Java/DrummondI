/**
 * 
 */
let img = document.getElementById('imgView');
console.log("AFDDFDDSAFDf");
console.log('img alt = ' + img.getAttribute('alt'));
let s = "../image/?id=" + img.getAttribute('alt');
console.log(s);

img.setAttribute('src', s);