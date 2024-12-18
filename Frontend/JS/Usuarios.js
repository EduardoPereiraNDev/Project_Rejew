document.addEventListener('DOMContentLoaded', function () {
    carregarListaUsuarios();
});

function carregarListaUsuarios() {
    fetch('http://localhost:8080/usuarios')
        .then(response => response.json())
        .then(usuarios => {
            const tabelaLista = document.getElementById('tabela_lista').getElementsByTagName('tbody')[0];
            tabelaLista.innerHTML = '';
            usuarios.forEach(usuario => {
                const row = tabelaLista.insertRow();
                row.insertCell(0).textContent = usuario.emailEntrada;
                row.insertCell(1).textContent = usuario.nomeUsuario;
                row.insertCell(2).textContent = usuario.nomePerfil;
                row.insertCell(3).textContent = new Date(usuario.dataNascimento).toLocaleDateString() || "Não constatado";
                row.insertCell(4).textContent = usuario.caminhoImagem;
                row.insertCell(5).textContent = usuario.caminhoImagemFundo;
                row.insertCell(6).textContent = usuario.recadoPerfil;

                // Botão Editar
                const cellEditar = document.createElement('td');
                const btnEditar = document.createElement('button');
                btnEditar.textContent = 'Editar';
                btnEditar.className = 'btn btn-secondary';
                btnEditar.addEventListener('click', () => editarUsuario(usuario.emailEntrada));
                cellEditar.appendChild(btnEditar);
                row.appendChild(cellEditar);

                // Botão Deletar
                const cellDeletar = document.createElement('td');
                const btnDeletar = document.createElement('button');
                btnDeletar.textContent = 'Deletar';
                btnDeletar.className = 'btn btn-danger';
                btnDeletar.addEventListener('click', () => deletarUsuario(usuario.emailEntrada));
                cellDeletar.appendChild(btnDeletar);
                row.appendChild(cellDeletar);
            });
        });
}

function editarUsuario(email) {
    const button = event.target;
    const linha = button.closest('tr');
    const cells = linha.getElementsByTagName('td');

    for (let i = 1; i < cells.length - 1; i++) {
        const valor = cells[i].textContent.trim();
        let input = null;
        if (i === 3) {
            input = document.createElement('input');
            input.type = 'date';
            input.value = valor;
        } else if (i === 4 || i === 5) {
            input = document.createElement('input');
            input.type = 'file';
            input.accept = "image/*";
        } else {
            input = document.createElement('input');
            input.type = 'text';
            input.value = valor;
        }

        cells[i].textContent = '';
        cells[i].appendChild(input);
    }

    const salvarButton = document.createElement('button');
    salvarButton.textContent = 'Salvar';
    salvarButton.className = 'btn btn-primary';
    salvarButton.onclick = function () {
        salvarEdicao(linha, email);
    };

    const cancelarButton = document.createElement('button');
    cancelarButton.textContent = 'Cancelar';
    cancelarButton.className = 'btn btn-danger';
    cancelarButton.onclick = function () {
        carregarListaUsuarios();
    };

    cells[cells.length - 1].innerHTML = '';
    cells[cells.length - 1].appendChild(cancelarButton);

    cells[cells.length - 2].innerHTML = '';
    cells[cells.length - 2].appendChild(salvarButton);
}

async function salvarEdicao(linha, email) {
    try {
        const inputs = linha.getElementsByTagName('input');
        const formData = new FormData();

        formData.append('nomeUsuario', inputs[0].value.trim());
        formData.append('nomePerfil', inputs[1].value.trim());
        formData.append('emailEntrada', email);
        formData.append('dataNascimento', inputs[2].value.trim());

        const caminhoImgPerfil = inputs[3].files[0];
        const caminhoImgFundo = inputs[4].files[0];
        formData.append('caminhoImagem', caminhoImgPerfil);
        formData.append('caminhoImagemFundo', caminhoImgFundo);
        formData.append('recadoPerfil', inputs[5].value.trim());

        const url = `http://localhost:8080/usuarios/${email}`;
        const requestOptions = {
            method: 'PUT',
            body: formData
        };

        fetch(url, requestOptions)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Falha ao atualizar o Usuário. Código de status: ${response.status}`);
                }
                Swal.fire({
                    icon: 'success',
                    title: 'Sucesso',
                    text: 'O Usuário foi atualizado com sucesso.'
                });
                carregarListaUsuarios();
            })
            .catch(error => {
                Swal.fire({
                    icon: 'error',
                    title: 'Erro',
                    text: `Erro ao tentar atualizar o Usuário. ${error.message}`
                });
                console.error('Erro ao tentar atualizar o Usuário:', error);
            });
    } catch (error) {
        Swal.fire({
            icon: 'error',
            title: 'Atenção',
            text: `Dados Inválidos ou Vazios. Erro: ${error.message}`
        });
        console.error(error);
    }
}


function deletarUsuario(email) {
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
            fetch(`http://localhost:8080/usuarios/${email}`, {
                method: 'DELETE'
            })
                .then(response => {
                    if (response.ok) {
                        Swal.fire('Sucesso!', 'Usuário deletado com sucesso!', 'success');
                        carregarListaUsuarios();
                    } else {
                        Swal.fire('Erro!', 'Erro ao deletar usuário!', 'error');
                    }
                })
                .catch(error => {
                    Swal.fire('Erro!', 'Erro ao deletar usuário!', 'error');
                });
        }
    });
}

document.getElementById('buttonPesquisar').addEventListener('click', async function () {
    const busca = document.getElementById("pesquisa").value;

    if (busca.trim() === '') {
        Swal.fire({
            icon: 'warning',
            title: 'Atenção',
            text: 'Por favor, insira um email ou nome para buscar.'
        });
        return;
    }

    const url = `http://localhost:8080/usuarios/email/${busca}`;
    const url2 = `http://localhost:8080/usuarios/perfil/${busca}`;

    try {
        const [response, response2] = await Promise.all([
            fetch(url, { method: 'GET', headers: { 'Content-Type': 'application/json' } }),
            fetch(url2, { method: 'GET', headers: { 'Content-Type': 'application/json' } })
        ]);

        if (!response.ok && !response2.ok) {
            Swal.fire({
                icon: 'info',
                title: 'Resultado da Busca',
                text: 'Nenhum Usuário com este email ou nome encontrado.'
            });
            return;
        }

        const data = await response.json();
        const data2 = await response2.json();
        
        const todosUsuarios = [...data, ...data2];

        const usuariosUnicos = Array.from(new Map(todosUsuarios.map(usuario => [usuario.emailEntrada, usuario])).values());

        if (usuariosUnicos.length === 0) {
            Swal.fire({
                icon: 'info',
                title: 'Resultado da Busca',
                text: 'Nenhum Usuário com este email ou nome encontrado.'
            });
            return;
        }

        const tabelaLista = document.getElementById('tabela_lista').getElementsByTagName('tbody')[0];
        tabelaLista.innerHTML = '';

        function adicionarUsuarios(usuarios) {
            usuarios.forEach(usuario => {
                const row = tabelaLista.insertRow();
                row.insertCell(0).textContent = usuario.emailEntrada;
                row.insertCell(1).textContent = usuario.nomeUsuario;
                row.insertCell(2).textContent = usuario.nomePerfil;
                row.insertCell(3).textContent = new Date(usuario.dataNascimento).toLocaleDateString();
                row.insertCell(4).textContent = usuario.caminhoImagem;
                row.insertCell(5).textContent = usuario.caminhoImagemFundo;
                row.insertCell(6).textContent = usuario.recadoPerfil;

                // Botão Editar
                const cellEditar = document.createElement('td');
                const btnEditar = document.createElement('button');
                btnEditar.textContent = 'Editar';
                btnEditar.className = 'btn btn-secondary';
                btnEditar.addEventListener('click', () => editarUsuario(usuario.emailEntrada));
                cellEditar.appendChild(btnEditar);
                row.appendChild(cellEditar);

                // Botão Deletar
                const cellDeletar = document.createElement('td');
                const btnDeletar = document.createElement('button');
                btnDeletar.textContent = 'Deletar';
                btnDeletar.className = 'btn btn-danger';
                btnDeletar.addEventListener('click', () => deletarUsuario(usuario.emailEntrada));
                cellDeletar.appendChild(btnDeletar);
                row.appendChild(cellDeletar);
            });
        }

        adicionarUsuarios(usuariosUnicos);

    } catch (error) {
        Swal.fire({
            icon: 'error',
            title: 'Atenção',
            text: `Erro ao buscar o Usuário: ${error.message}`
        });
    }
});


document.getElementById('buttonApagar').addEventListener('click', function () {
    document.getElementById("pesquisa").value = "";
    carregarListaUsuarios();
});
