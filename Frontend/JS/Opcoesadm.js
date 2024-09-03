let md = 1;
function myFunction() {
    let element = document.body;
    element.classList.toggle("dark-mode");
    let elementos = document.getElementsByClassName("card");

    if (md == 0) {
        for (let i = 0; i < elementos.length; i++) {
            elementos[i].style.backgroundColor = "#3394d4"
            md = 1;
        }
    } else {
        for (let i = 0; i < elementos.length; i++) {
            elementos[i].style.backgroundColor = "#3bc7ea"
            md = 0
        }
    }
}

