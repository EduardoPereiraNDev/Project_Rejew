document.addEventListener('DOMContentLoaded', function () {
    carregarListaChat();
    carregarGeneros();

});
function carregarListaChat() {
    fetch('http://localhost:8080/chats')
        .then(response => response.json())
        .then(chats => {
            const tabelaLista = document.getElementById('tabela_lista').getElementsByTagName('tbody')[0];
            tabelaLista.innerHTML = '';
            chats.forEach(chat => {
                const row = tabelaLista.insertRow();
                row.insertCell(0).textContent = chat.idChat;
                row.insertCell(1).textContent = chat.generoChat;
                row.insertCell(2).textContent = chat.caminhoImagemLogo;
                row.insertCell(3).textContent = chat.caminhoImagemFundoChat;

                //Botao Editar

                const cellEditar = document.createElement('td');
                const btnEditar = document.createElement('button');
                btnEditar.textContent = 'Editar';
                btnEditar.className = 'btn btn-secondary';
                btnEditar.onclick = function () {
                    editarChat(event, chat.idChat);
                };
                cellEditar.appendChild(btnEditar);
                row.appendChild(cellEditar);

                //Botao excluir
                const cellExcluir = document.createElement('td');
                const btnExcluir = document.createElement('button');
                btnExcluir.textContent = 'Excluir';
                btnExcluir.className = 'btn btn-danger';
                btnExcluir.onclick = function () {
                    excluirChat(chat.idChat);
                };
                cellExcluir.appendChild(btnExcluir);
                row.appendChild(cellExcluir);

            });
        })
        .catch(error => console.error('Erro ao carregar lista:', error));
}

function carregarChatGenero() {
    const busca = document.getElementById('pesquisa').value;
    const url = `http://localhost:8080/chats/chat/${busca}`
    const requestOptions = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    };
    fetch(url, requestOptions)
        .then(response => response.json())
        .then(chats => {
            const tabelaLista = document.getElementById('tabela_lista').getElementsByTagName('tbody')[0];
            tabelaLista.innerHTML = '';
            chats.forEach(chat => {
                const row = tabelaLista.insertRow();
                row.insertCell(0).textContent = chat.idChat;
                row.insertCell(1).textContent = chat.generoChat;
                row.insertCell(2).textContent = chat.caminhoImagemLogo;
                row.insertCell(3).textContent = chat.caminhoImagemFundoChat;

                //Botao Editar

                const cellEditar = document.createElement('td');
                const btnEditar = document.createElement('button');
                btnEditar.textContent = 'Editar';
                btnEditar.className = 'btn btn-secondary';
                btnEditar.onclick = function () {
                    editarGenero(event, chat.idChat);
                };
                cellEditar.appendChild(btnEditar);
                row.appendChild(cellEditar);

                // Botão Excluir
                const cellExcluir = document.createElement('td');
                const btnExcluir = document.createElement('button');
                btnExcluir.textContent = 'Excluir';
                btnExcluir.className = 'btn btn-danger';
                btnExcluir.onclick = () => excluirGenero(chat.idChat);
                cellExcluir.appendChild(btnExcluir);
                row.appendChild(cellExcluir);
            });
        })
        .catch(error => console.error('Erro ao buscar chats:', error))
}

function carregarGeneros() {
    fetch('http://localhost:8080/generos')
        .then(response => response.json())
        .then(generos => {
            let select = document.getElementById('generoChat');
            generos.forEach(genero => {
                let option = document.createElement('option');
                option.value = genero.idGenero;
                option.textContent = genero.generoLivro;
                select.appendChild(option);
            });
        })
        .catch(error => console.error('Erro ao carregar os dados:', error));
}

async function carregarGeneroEsp(idGenero) {
    const url = `http://localhost:8080/generos/${idGenero}`;
    const requestOptions = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    };
    try {
        const response = await fetch(url, requestOptions);
        if (!response.ok) {
            throw new Error(`Falha ao buscar o gênero. Código de status: ${response.status}`);
        }
        const data = await response.json();
        return data.generoLivro;
    } catch (error) {
        console.error('Erro ao carregar o gênero:', error);
        return null;
    }
}

// Função para adicionar um genero
const formCadastro = document.getElementById('form_cadastro');
formCadastro.addEventListener('submit', async function (event) {
    event.preventDefault();

    const select = document.getElementById('generoChat');
    const ChatGeneroValue = select.options[select.selectedIndex].value;
    const generoChat = await carregarGeneroEsp(ChatGeneroValue);
    if (generoChat === null || generoChat === undefined) {
        Swal.fire({
            icon: 'error',
            title: 'Atenção',
            text: 'Há alguns dados não preenchidos ou houve um erro ao obter o gênero.'
        });
        return;
    }
    const caminhoImagemLogo = document.getElementById('caminhoImagemLogo').files[0];
    const caminhoImagemFundoChat = document.getElementById('caminhoImagemFundoChat').files[0];

    const formData = new FormData();
    formData.append('generoChat', generoChat);
    formData.append('caminhoImagemLogo', caminhoImagemLogo);
    formData.append('caminhoImagemFundoChat', caminhoImagemFundoChat);

    fetch(`http://localhost:8080/chats/chat/${generoChat}`)
        .then(response => response.json())
        .then(chats => {
            if (chats[0] != null) {
                Swal.fire({
                    icon: 'warning',
                    title: 'Erro ao adicionar chat',
                    text: 'Já existe um chat deste genero'
                });
                return;
            }
            return fetch('http://localhost:8080/chats', {
                method: 'POST',
                body: formData
            })
        })
        .then(response => response.text())
        .then(data => {
            alert("Chat Adicionado");
            carregarListaChat();
            formCadastro.reset();
        })
        .catch(error => console.error('Erro ao cadastrar Chats:', error));
})




//Função EDITAR Genero
function editarChat(event, id) {
    const button = event.target;
    const linha = button.closest('tr');
    const cells = linha.getElementsByTagName('td');
    let generoID = "";

    for (let i = 1; i < cells.length - 2; i++) {
        const Valor = cells[i].textContent.trim();
        let input = document.createElement('input');
        if (i == 1) {
            const genero = Valor;
            const select = document.createElement('select');
            select.id = "generosN";
            const url = `http://localhost:8080/generos/genero/${encodeURIComponent(genero)}`;
            const requestOptions = {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            };
            fetch(url, requestOptions)
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`Falha ao buscar o gênero pelo nome. Código de status: ${response.status}`);
                    }
                    return response.json();
                })
                .then(generoData => {
                    const generoDataP = generoData[0];
                    generoID = generoDataP.idGenero
                    console.log(generoID);
                })
                .catch(error => console.error('Erro ao carregar os dados:', error));
            fetch('http://localhost:8080/generos')
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`Falha ao buscar os generos. Código de status: ${response.status}`);
                    }
                    return response.json();
                })
                .then(generos => {
                    generos.forEach(genero => {
                        let option = document.createElement('option');
                        option.value = genero.idGenero;
                        option.textContent = genero.generoLivro;
                        select.appendChild(option);
                        if (generoID == option.value) {
                            select.value = option.value;
                        }
                    });

                    cells[i].textContent = '';
                    cells[i].appendChild(select);
                })
                .catch(error => console.error('Erro ao carregar os dados:', error));
        } else {
            input = document.createElement('input');
            input.type = 'file';
            input.id = `caminhoImagem_${i}`;
            input.accept = "image/*";
        }
        if (input !== null) {
            cells[i].textContent = '';
            cells[i].appendChild(input);
        }
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
        carregarListaChat();
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
    const select = document.getElementById("generosN");
    const ChatGeneroValue = select.options[select.selectedIndex].value;
    const generoChat = await carregarGeneroEsp(ChatGeneroValue);
    const caminhoImagemLogo = document.getElementById('caminhoImagem_2').files[0];
    const caminhoImagemFundoChat = document.getElementById('caminhoImagem_3').files[0];
    try {
        const inputs = linha.getElementsByTagName('input');
        const formData = new FormData();
        formData.append('generoChat', generoChat);
        formData.append('caminhoImagemLogo', caminhoImagemLogo);
        formData.append('caminhoImagemFundoChat', caminhoImagemFundoChat);
        const url = `http://localhost:8080/chats/${id}`;
        const requestOptions = {
            method: 'PUT',
            body: formData
        };
        fetch(url, requestOptions)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Falha ao atualizar o Chat. Código de status: ${response.status}`);
                }
                Swal.fire({
                    icon: 'success',
                    title: 'Sucesso',
                    text: 'O Chat foi atualizado com sucesso.'
                });
                carregarListaChat();
            })
            .catch(error => {
                Swal.fire({
                    icon: 'error',
                    title: 'Erro',
                    text: 'Erro ao tentar atualizar o Chat.'
                });
                console.error('Erro ao tentar atualizar o Chat:', error);
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
function excluirChat(id) {
    const url = `http://localhost:8080/chats/${id}`;
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
                        throw new Error(`Falha ao excluir o Chat. Código de status: ${response.status}`);
                    }

                    Swal.fire({
                        icon: 'success',
                        title: 'Atenção',
                        text: 'O Chat foi excluído com sucesso.'
                    });
                    carregarListaChat();
                })
                .catch(error => {
                    Swal.fire({
                        icon: 'error',
                        title: 'Atenção',
                        text: 'Erro ao tentar excluir o Chat.'
                    });
                    carregarListaChat();
                });
        }
    });
}

document.getElementById('buttonPesquisar').onclick = function () {
    carregarChatGenero();
};
document.getElementById('buttonApagar').onclick = function () {
    carregarListaChat();
    document.getElementById('pesquisa').value = "";
};
