document.addEventListener('DOMContentLoaded', function () {
    carregarListaAutores();
    carregarListaUsuarios();
});
function carregarListaAutores() {
    fetch('http://localhost:8080/autores')
        .then(response => response.json())
        .then(autores => {
            const tabelaLista = document.getElementById('tabela_lista').getElementsByTagName('tbody')[0];
            tabelaLista.innerHTML = '';
            autores.forEach(autor => {
                const row = tabelaLista.insertRow();
                row.insertCell(0).textContent = autor.idAutor;
                row.insertCell(1).textContent = autor.usuarioEmailEntrada;
                row.insertCell(2).textContent = autor.qtdLivros || "Sem Livros";


                //Botao excluir
                const cellExcluir = document.createElement('td');
                const btnExcluir = document.createElement('button');
                btnExcluir.textContent = 'Retirar';
                btnExcluir.className = 'btn btn-danger';
                btnExcluir.onclick = function () {
                    excluirAutor(autor.idAutor);
                };
                cellExcluir.appendChild(btnExcluir);
                row.appendChild(cellExcluir);

            });
        })
        .catch(error => console.error('Erro ao carregar lista:', error));
}

function carregarListaUsuarios() {
    fetch('http://localhost:8080/usuarios')
        .then(response => response.json())
        .then(usuarios => {
            const tabelaLista = document.getElementById('tabela_listaU').getElementsByTagName('tbody')[0];
            tabelaLista.innerHTML = '';
            usuarios.forEach(usuario => {
                const row = tabelaLista.insertRow();
                row.insertCell(0).textContent = usuario.emailEntrada;
                row.insertCell(1).textContent = usuario.nomeUsuario;
                row.insertCell(2).textContent = usuario.nomePerfil;

                //Botao Tornar Autor

                const cellAutor = document.createElement('td');
                const btnAutor = document.createElement('button');
                btnAutor.textContent = 'Tornar Autor';
                btnAutor.className = 'btn btn-primary';
                btnAutor.onclick = function () {
                    tornarAutor(usuario.emailEntrada);
                };
                cellAutor.appendChild(btnAutor);
                row.appendChild(cellAutor);

            });
        })
        .catch(error => console.error('Erro ao carregar lista:', error));
}


//Função para tornar usuario um Autor 
function tornarAutor(emailEntrada) {
    fetch(`http://localhost:8080/autores/usuarios/${emailEntrada}`)
        .then(response => response.json())
        .then(usuarios => {
            if (usuarios[0] != null) {
                Swal.fire({         
                    icon: 'warning',
                    title: 'Erro ao Tornar Autor',
                    text: 'O usuário já é um autor'
                });
                return;
            }
            const data = {
                usuarioEmailEntrada: emailEntrada,
            };
            return fetch('http://localhost:8080/autores', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            });
        })
        .then(response => response.text())
        .then(data => {
            alert("O usuário foi alterado como Autor");
            carregarListaAutores();
            carregarListaUsuarios();
        })
        .catch(error => console.error('Erro ao registrar o autor:', error));
}


// Função pra excluir Genero
function excluirAutor(id) {
    const url = `http://localhost:8080/autores/${id}`;
    const requestOptions = {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
        },
    };

    Swal.fire({
        title: 'Tem certeza?',
        text: 'Esta ação não pode ser revertida!',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        cancelButtonColor: '#3085d6',
        confirmButtonText: 'Sim, finalizar!',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.isConfirmed) {
            fetch(url, requestOptions)
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`Falha ao retitar modo Autor. Código de status: ${response.status}`);
                    }
                    Swal.fire({
                        icon: 'success',
                        title: 'Atenção',
                        text: 'O Usuario teve seu modo Autor retirado com sucesso.'
                    });
                    carregarListaAutores();
                    carregarListaUsuarios();
                })
                .catch(error => {
                    Swal.fire({
                        icon: 'error',
                        title: 'Atenção',
                        text: `Houve um problema ao tentar excluir o autor: ${error.message}`
                    });
                    carregarListaAutores();
                    carregarListaUsuarios();
                });
        }
    });
}

document.getElementById('buttonPesquisar').onclick = function () {
    carregarListaAutores();
    carregarListaUsuarios();
};
document.getElementById('buttonApagar').onclick = function () {
    carregarListaAutores();
    carregarListaUsuarios();
    document.getElementById('pesquisa').value = "";
};
