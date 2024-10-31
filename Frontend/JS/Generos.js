document.addEventListener('DOMContentLoaded', function () {
    carregarListaGeneros();

});
function carregarListaGeneros() {
    fetch('http://localhost:8080/generos')
        .then(response => response.json())
        .then(generos => {
            const tabelaLista = document.getElementById('tabela_lista').getElementsByTagName('tbody')[0];
            tabelaLista.innerHTML = '';
            generos.forEach(genero => {
                const row = tabelaLista.insertRow();
                row.insertCell(0).textContent = genero.idGenero;
                row.insertCell(1).textContent = genero.generoLivro;
                
                //Botao Editar

                const cellEditar = document.createElement('td');
                const btnEditar = document.createElement('button');
                btnEditar.textContent = 'Editar';
                btnEditar.className = 'btn btn-secondary';
                btnEditar.onclick = function () {
                    editarGenero(event, genero.idGenero);
                };
                cellEditar.appendChild(btnEditar);
                row.appendChild(cellEditar);

                //Botao excluir
                const cellExcluir = document.createElement('td');
                const btnExcluir = document.createElement('button');
                btnExcluir.textContent = 'Excluir';
                btnExcluir.className = 'btn btn-danger';
                btnExcluir.onclick = function () {
                    excluirGenero(genero.idGenero);
                };
                cellExcluir.appendChild(btnExcluir);
                row.appendChild(cellExcluir);

            });
        })
        .catch(error => console.error('Erro ao carregar lista:', error));
}

function carregarGeneroNome() {
    const busca = document.getElementById('pesquisa').value;
    const url = `http://localhost:8080/generos/genero/${busca}`
    const requestOptions = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    };
    fetch(url, requestOptions)
        .then(response => response.json())
        .then(generos => {
            const tabelaLista = document.getElementById('tabela_lista').getElementsByTagName('tbody')[0];
            tabelaLista.innerHTML = '';
            generos.forEach(genero => {
                const row = tabelaLista.insertRow();
                row.insertCell(0).textContent = genero.idGenero;
                row.insertCell(1).textContent = genero.generoLivro;

                 //Botao Editar

                const cellEditar = document.createElement('td');
                const btnEditar = document.createElement('button');
                btnEditar.textContent = 'Editar';
                btnEditar.className = 'btn btn-secondary';
                btnEditar.onclick = function () {
                    editarGenero(event, genero.idGenero);
                };
                cellEditar.appendChild(btnEditar);
                row.appendChild(cellEditar);

                // Botão Excluir
                const cellExcluir = document.createElement('td');
                const btnExcluir = document.createElement('button');
                btnExcluir.textContent = 'Excluir';
                btnExcluir.className = 'btn btn-danger';
                btnExcluir.onclick = () => excluirGenero(genero.idGenero); 
                cellExcluir.appendChild(btnExcluir);
                row.appendChild(cellExcluir);
            });
        })
        .catch(error => console.error('Erro ao buscar generos:', error))
}


// Função para adicionar um genero
const formCadastro = document.getElementById('form_cadastro');
formCadastro.addEventListener('submit', async function (event) {
    event.preventDefault();
    const data = {
        generoLivro: document.getElementById('GeneroLivro').value
    };
    fetch('http://localhost:8080/generos', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => response.text())
        .then(data => {
            alert("Genero Adicionado");
            carregarListaGeneros();
            formCadastro.reset();
        })
        .catch(error => console.error('Erro ao cadastrar Genero:', error));
});

//Função EDITAR Genero
function editarGenero(event, id) {
    const button = event.target;
    const linha = button.closest('tr');
    const cells = linha.getElementsByTagName('td');

    for (let i = 1; i < cells.length - 2; i++) {
        const Valor = cells[i].textContent.trim();
        let input = document.createElement('input');
        input.type = 'text';
        input.value = Valor;
        input.className = 'textArea';
        cells[i].textContent = '';
        cells[i].appendChild(input);
    }

    const salvarButton = document.createElement('button');
    salvarButton.textContent = 'Salvar';
    salvarButton.className = 'btn btn-primary';
    salvarButton.onclick = function () {
        salvarEdicao(linha, id);
    };

    const cancelarButton = document.createElement('button');
    cancelarButton.textContent = 'Cancelar';
    cancelarButton.className = 'btn btn-danger';
    cancelarButton.onclick = function () {
        tabela.classList.remove('edit')
        carregarListaGeneros();
    };

    cells[cells.length - 1].innerHTML = '';
    cells[cells.length - 1].appendChild(cancelarButton);

    cells[cells.length - 2].innerHTML = '';
    cells[cells.length - 2].appendChild(salvarButton);

    const tabela = document.getElementsByClassName('tab-content')[0];
    tabela.classList.add('edit');

    const textAreas = document.getElementsByClassName('textArea');
    for (let i = 0; i < textAreas.length; i++) {
        textAreas[i].addEventListener('focus', () => {
            textAreas[i].classList.add('expanded');
        });

        textAreas[i].addEventListener('blur', () => {
            textAreas[i].classList.remove('expanded');
        });
    }
}

//Função salvar edição
async function salvarEdicao(linha, id) {
    try {
        const inputs = linha.getElementsByTagName('input');
        const data = {
            generoLivro: inputs[0].value.trim(),
        };
        const url = `http://localhost:8080/generos/${id}`;
        const requestOptions = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        };
        fetch(url, requestOptions)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Falha ao atualizar o Genero. Código de status: ${response.status}`);
                }
                Swal.fire({
                    icon: 'success',
                    title: 'Sucesso',
                    text: 'O Genero foi atualizado com sucesso.'
                });
                carregarListaGeneros();
            })
            .catch(error => {
                Swal.fire({
                    icon: 'error',
                    title: 'Erro',
                    text: 'Erro ao tentar atualizar o Genero.'
                });
                console.error('Erro ao tentar atualizar o Genero:', error);
            });
    } catch (error) {
        Swal.fire({
            icon: 'error',
            title: 'Atenção',
            text: `Dados Invalidos Ou Vazios, Error: ${error.message}`
        });
        console.error(error);
    }
}

// Função pra excluir Genero
function excluirGenero(id) {
    const url = `http://localhost:8080/generos/${id}`;
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
                        throw new Error(`Falha ao excluir o genero. Código de status: ${response.status}`);
                    }

                    Swal.fire({
                        icon: 'success',
                        title: 'Atenção',
                        text: 'O Genero foi excluído com sucesso.'
                    });
                    carregarListaGeneros();
                })
                .catch(error => {
                    Swal.fire({
                        icon: 'error',
                        title: 'Atenção',
                        text: 'Erro ao tentar excluir o Genero.'
                    });
                    carregarListaGeneros();
                });
        }
    });
}

document.getElementById('buttonPesquisar').onclick = function () {
    carregarGeneroNome();
};
document.getElementById('buttonApagar').onclick = function () {
    carregarListaGeneros();
    document.getElementById('pesquisa').value = "";
};
