document.addEventListener('DOMContentLoaded', function () {
    carregarListaLivros();
    carregarGeneros();
    const img = document.createElement('img');
    img.id = 'imgFile';
    img.style.maxWidth = '200px';
    img.style.maxHeight = '200px';
    img.src = 'https://thumbs.dreamstime.com/b/%C3%ADcone-de-imagem-sem-foto-ou-em-branco-carregamento-imagens-aus%C3%AAncia-marca-n%C3%A3o-dispon%C3%ADvel-sinal-breve-silhueta-natureza-simples-215973362.jpg';
    document.getElementById('imageDisplay').appendChild(img);

    const colorPicker = document.getElementById('escolha_cor');
    const selectedColor = document.getElementById('selectedColor');
    const colorDisplay = document.getElementById('colorDisplay');
    colorPicker.addEventListener('input', function () {
        document.getElementById('colorDisplay').innerHTML = '';
        const color = colorPicker.value;
        selectedColor.textContent = color;
        colorDisplay.style.backgroundColor = color;
    });
});

document.getElementById('caminhoImgCapa').addEventListener('change', function (event) {
    const file = event.target.files[0];
    if (file) {
        const file2 = new FileReader();
        file2.onload = function (e) {
            const img = document.getElementById('imgFile');
            img.src = e.target.result;
            img.style.marginBottom = '20px';
            document.getElementById('imageDisplay').innerHTML = '';
            document.getElementById('imageDisplay').appendChild(img);
            //botao remover IMG
            const buttonRemoveImg = document.createElement('button');
            buttonRemoveImg.textContent = 'Remover';
            buttonRemoveImg.className = 'btn btn-secondary';
            buttonRemoveImg.id = 'buttonRemoveImg';
            buttonRemoveImg.onclick = function () {
                img.src = 'https://thumbs.dreamstime.com/b/%C3%ADcone-de-imagem-sem-foto-ou-em-branco-carregamento-imagens-aus%C3%AAncia-marca-n%C3%A3o-dispon%C3%ADvel-sinal-breve-silhueta-natureza-simples-215973362.jpg';
                document.getElementById('imageDisplay').removeChild(buttonRemoveImg);
                document.getElementById('caminhoImgCapa').value = '';
            };
            document.getElementById('imageDisplay').appendChild(buttonRemoveImg);
        };
        file2.readAsDataURL(file);
    }
});

// Função para carregar dados dos livros
function carregarListaLivros() {
    fetch('http://localhost:8080/livros')
        .then(response => response.json())
        .then(livros => {
            const tabelaLista = document.getElementById('tabela_lista').getElementsByTagName('tbody')[0];
            tabelaLista.innerHTML = '';
            livros.forEach(livro => {
                const row = tabelaLista.insertRow();
                row.insertCell(0).textContent = livro.isbnLivro;
                row.insertCell(1).textContent = livro.nomeLivro;
                row.insertCell(2).textContent = livro.autorLivro;
                row.insertCell(3).textContent = livro.sinopseLivro;
                row.insertCell(4).textContent = livro.numeroPag;
                row.insertCell(5).textContent = livro.anoLancamento;
                row.insertCell(6).textContent = livro.NotaLivro || "Sem Notas";
                row.insertCell(7).textContent = livro.qtdComentario || "Nenhum comentario";
                row.insertCell(8).textContent = livro.caminhoImgCapa;
                row.insertCell(9).textContent = livro.corPrimaria;
                row.insertCell(10).textContent = livro.generoLivro;


                //Botao Editar

                const cellEditar = document.createElement('td');
                const btnEditar = document.createElement('button');
                btnEditar.textContent = 'Editar';
                btnEditar.className = 'btn btn-secondary';
                btnEditar.onclick = function () {
                    editarLivro(event, livro.isbnLivro);
                };
                cellEditar.appendChild(btnEditar);
                row.appendChild(cellEditar);

                //Botao excluir
                const cellExcluir = document.createElement('td');
                const btnExcluir = document.createElement('button');
                btnExcluir.textContent = 'Excluir';
                btnExcluir.className = 'btn btn-danger';
                btnExcluir.onclick = function () {
                    excluirLivro(livro.isbnLivro);
                };
                cellExcluir.appendChild(btnExcluir);
                row.appendChild(cellExcluir);

            });
        })
        .catch(error => console.error('Erro ao carregar lista:', error));
}

// Função para carregar gêneros
function carregarGeneros() {
    fetch('http://localhost:8080/generos')
        .then(response => response.json())
        .then(generos => {
            let select = document.getElementById('generos');
            generos.forEach(genero => {
                let option = document.createElement('option');
                option.value = genero.idGenero;
                option.textContent = genero.generoLivro;
                select.appendChild(option);
            });
        })
        .catch(error => console.error('Erro ao carregar os dados:', error));
}



// Função pra obter o nome do gênero 
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

//Função pra procurar livro por nome

async function carregarLivroNome() {
    const Busca = document.getElementById("pesquisa").value;

    if (Busca.trim() === '') {
        Swal.fire({
            icon: 'warning',
            title: 'Atenção',
            text: 'Por favor, insira um nome para buscar.'
        });
        return;
    }

    const url = `http://localhost:8080/livros/nome/${encodeURIComponent(Busca)}`;
    const url2 = `http://localhost:8080/livros/autor/${encodeURIComponent(Busca)}`;

    try {
        const [response, response2] = await Promise.all([
            fetch(url, { method: 'GET', headers: { 'Content-Type': 'application/json' } }),
            fetch(url2, { method: 'GET', headers: { 'Content-Type': 'application/json' } })
        ]);

        if (!response.ok && !response2.ok) {
            Swal.fire({
                icon: 'info',
                title: 'Resultado da Busca',
                text: 'Nenhum Livro com este nome ou autor encontrado.'
            });
            return;
        }

        const data = await response.json();
        const data2 = await response2.json();

        const livros = Array.isArray(data) ? data : [data];
        const livros2 = Array.isArray(data2) ? data2 : [data2];

        if (livros.length === 0 && livros2.length === 0) {
            Swal.fire({
                icon: 'info',
                title: 'Resultado da Busca',
                text: 'Nenhum Livro com este nome ou autor encontrado.'
            });
            return;
        }

        const tabelaLista = document.getElementById('tabela_lista').getElementsByTagName('tbody')[0];
        tabelaLista.innerHTML = '';

        function adicionarLivros(livros) {
            livros.forEach(livro => {
                const row = tabelaLista.insertRow();
                row.insertCell(0).textContent = livro.isbnLivro;
                row.insertCell(1).textContent = livro.nomeLivro;
                row.insertCell(2).textContent = livro.autorLivro;
                row.insertCell(3).textContent = livro.sinopseLivro;
                row.insertCell(4).textContent = livro.numeroPag;
                row.insertCell(5).textContent = livro.anoLancamento;
                row.insertCell(6).textContent = livro.NotaLivro || "Sem Notas";
                row.insertCell(7).textContent = livro.qtdComentario || "Nenhum comentario";
                row.insertCell(8).textContent = livro.caminhoImgCapa;
                row.insertCell(9).textContent = livro.corPrimaria;
                row.insertCell(10).textContent = livro.generoLivro;

                // Botão Editar
                const cellEditar = document.createElement('td');
                const btnEditar = document.createElement('button');
                btnEditar.textContent = 'Editar';
                btnEditar.className = 'btn btn-secondary';
                btnEditar.onclick = () => editarLivro(livro.isbnLivro);
                cellEditar.appendChild(btnEditar);
                row.appendChild(cellEditar);

                // Botão Excluir
                const cellExcluir = document.createElement('td');
                const btnExcluir = document.createElement('button');
                btnExcluir.textContent = 'Excluir';
                btnExcluir.className = 'btn btn-danger';
                btnExcluir.onclick = () => excluirLivro(livro.isbnLivro);
                cellExcluir.appendChild(btnExcluir);
                row.appendChild(cellExcluir);
            });
        }
        adicionarLivros(livros);
        adicionarLivros(livros2);

    } catch (error) {
        Swal.fire({
            icon: 'error',
            title: 'Atenção',
            text: `Erro ao buscar o Livro: ${error.message}`
        });
    }
}
//Funcao livro pelo ISBN
function carregarLivroIsbn(isbn) {
    const url = `http://localhost:8080/livros/${isbn}`;
    const requestOptions = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    };
    fetch(url, requestOptions)
        .then(response => {
            if (!response.ok) {
                throw new Error(`Falha ao buscar o livro. Código de status: ${response.status}`);
            }
            return response.json();
        })
        .then(livro => {
            console.log('Dados dos livros encontrados:', livro);
            return livro;
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'Atenção',
                text: `Erro ao buscar o lirvo: ${error.message}`
            });
        });
}

// Função para adicionar um livro
const formCadastro = document.getElementById('form_cadastro');
formCadastro.addEventListener('submit', async function (event) {
    event.preventDefault();
    const nomeLivro = document.getElementById('nomeLivro').value;
    const autorLivro = document.getElementById('autorLivro').value;
    const sinopseLivro = document.getElementById('sinopseLivro').value;
    const numeroPag = parseInt(document.getElementById('numeroPag').value);
    const anoLancamento = parseInt(document.getElementById('anoLancamento').value);
    const select = document.getElementById('generos');
    const generoValue = select.options[select.selectedIndex].value;
    const generoLivro = await carregarGeneroEsp(generoValue);
    if (generoLivro === null || generoLivro === undefined) {
        Swal.fire({
            icon: 'error',
            title: 'Atenção',
            text: 'Há alguns dados não preenchidos ou houve um erro ao obter o gênero.'
        });
        return;
    }
    const caminhoImgCapa = document.getElementById('caminhoImgCapa').files[0];

    const corFinal = document.getElementById('selectedColor').textContent;

    //FormData serve para enviar diferentes tipos de dados, como imagens,arquivos, textos e numeros

    const formData = new FormData();
    formData.append('nomeLivro', nomeLivro);
    formData.append('autorLivro', autorLivro);
    formData.append('sinopseLivro', sinopseLivro);
    formData.append('numeroPag', numeroPag);
    formData.append('anoLancamento', anoLancamento);
    formData.append('generoLivro', generoLivro);
    formData.append('caminhoImgCapa', caminhoImgCapa);
    formData.append('corPrimaria', corFinal);

    fetch('http://localhost:8080/livros', {
        method: 'POST',
        body: formData
    })
        .then(response => response.text())
        .then(data => {
            alert(data);
            carregarListaLivros();
            formCadastro.reset();
            const selectedColor = document.getElementById('selectedColor');
            const colorDisplay = document.getElementById('colorDisplay');
            selectedColor.textContent = "#ff0000";
            colorDisplay.style.backgroundColor = '#ff0000';
            const img = document.getElementById('imgFile');
            const buttonRemoveImg = document.getElementById('buttonRemoveImg');
            img.src = 'https://thumbs.dreamstime.com/b/%C3%ADcone-de-imagem-sem-foto-ou-em-branco-carregamento-imagens-aus%C3%AAncia-marca-n%C3%A3o-dispon%C3%ADvel-sinal-breve-silhueta-natureza-simples-215973362.jpg';
            document.getElementById('imageDisplay').removeChild(buttonRemoveImg);
            document.getElementById('caminhoImgCapa').value = '';
        })
        .catch(error => console.error('Erro ao cadastrar livro:', error));
});

//Função EDITAR LIVRO
function editarLivro(event, isbn) {
    const button = event.target;
    const linha = button.closest('tr');
    const cells = linha.getElementsByTagName('td');
    let generoID = "";

    for (let i = 0; i < cells.length - 2; i++) {
        const Valor = cells[i].textContent.trim();
        let input = null;

        if (i === cells.length - 8 || i === cells.length - 9) {
            input = document.createElement('input');
            input.type = 'number';
            input.value = Valor;
            input.className = 'textArea';
        } else if (i === 0 || i === 6 || i === 7) {
            cells[i].textContent = '';
            const text = document.createTextNode(Valor);
            cells[i].appendChild(text);
        } else if (i === cells.length - 3) {
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
                    generoID = generoDataP.idGenero;
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
        } else if (i === 8) {
            input = document.createElement('input');
            input.type = 'file';
            input.id = 'caminhoImgCapaEdit';
            input.accept = "image/*"
        } else if (i === 9) {
            input = document.createElement('input');
            input.type = 'color';
            input.className = 'CorPrimaria';
            input.value = Valor;
        }
        else {
            input = document.createElement('input');
            input.type = 'text';
            input.value = Valor;
            input.className = 'textArea';
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
        salvarEdicao(linha, isbn);
    };

    const cancelarButton = document.createElement('button');
    cancelarButton.textContent = 'Cancelar';
    cancelarButton.className = 'btn btn-danger';
    cancelarButton.onclick = function () {
        tabela.classList.remove('edit')
        carregarListaLivros();
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
async function salvarEdicao(linha, isbn) {
    try {
        const select = document.getElementById('generosN');
        const CorPrimaria = document.getElementsByClassName("CorPrimaria")[0].value;
        const caminhoImgCapa = document.getElementById('caminhoImgCapaEdit').files[0];
        const generoValue = select.options[select.selectedIndex].value;
        const generoLivro = await carregarGeneroEsp(generoValue);
        const inputs = linha.getElementsByTagName('input');
        const formData = new FormData();
        formData.append('nomeLivro', inputs[0].value.trim());
        formData.append('autorLivro', inputs[1].value.trim());
        formData.append('sinopseLivro', inputs[2].value.trim());
        formData.append('numeroPag', parseInt(inputs[3].value.trim()));
        formData.append('anoLancamento', parseInt(inputs[4].value.trim()));
        formData.append('generoLivro', generoLivro);
        formData.append('caminhoImgCapa', caminhoImgCapa);
        formData.append('corPrimaria', CorPrimaria);

        const url = `http://localhost:8080/livros/${isbn}`;
        const requestOptions = {
            method: 'PUT',
            body: formData
        };
        fetch(url, requestOptions)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Falha ao atualizar o Livro. Código de status: ${response.status}`);
                }
                Swal.fire({
                    icon: 'success',
                    title: 'Sucesso',
                    text: 'O Livro foi atualizado com sucesso.'
                });
                carregarListaLivros();
            })
            .catch(error => {
                Swal.fire({
                    icon: 'error',
                    title: 'Erro',
                    text: 'Erro ao tentar atualizar o Livro.'
                });
                console.error('Erro ao tentar atualizar o Livro:', error);
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
// Função pra excluir livro
function excluirLivro(isbn) {
    const url = `http://localhost:8080/livros/${isbn}`;
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
                        throw new Error(`Falha ao excluir o livro. Código de status: ${response.status}`);
                    }

                    Swal.fire({
                        icon: 'success',
                        title: 'Atenção',
                        text: 'O livro foi excluído com sucesso.'
                    });
                    carregarListaLivros();
                })
                .catch(error => {
                    Swal.fire({
                        icon: 'error',
                        title: 'Atenção',
                        text: 'Erro ao tentar excluir o livro.'
                    });
                    carregarListaLivros();
                });
        }
    });
}

document.getElementById('buttonPesquisar').onclick = function () {
    carregarLivroNome();
};
document.getElementById('buttonApagar').onclick = function () {
    carregarListaLivros();
    document.getElementById('pesquisa').value = "";
};
