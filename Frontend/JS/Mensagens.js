document.addEventListener('DOMContentLoaded', function () {
    carregarListaMensagens();
});

function carregarListaMensagens() {

    fetch('http://localhost:8080/mensagens')
        .then(response => response.json())
        .then(mensagens => {
            const tabelaLista = document.getElementById('tabela_lista').getElementsByTagName('tbody')[0];
            tabelaLista.innerHTML = '';
            mensagens.forEach(mensagem => {
                const row = tabelaLista.insertRow();
                row.insertCell(0).textContent = mensagem.idMensagem;
                row.insertCell(1).textContent = mensagem.textoMensagem;
                row.insertCell(2).textContent = mensagem.dataMensagem;
                row.insertCell(3).textContent = mensagem.usuarioMensagem;
                row.insertCell(4).textContent = mensagem.chatMensagem;

                const cellExcluir = document.createElement('td');

                // Botão Excluir
                const btnExcluir = document.createElement('button');
                btnExcluir.textContent = 'Excluir';
                btnExcluir.className = 'btn btn-danger';
                btnExcluir.onclick = function () {
                    excluirMensagem(mensagem.idMensagem);
                };
                cellExcluir.appendChild(btnExcluir);
                row.appendChild(cellExcluir);
            });
        })
        .catch(error => console.error('Erro ao carregar lista:', error));
}

async function carregarMensagensPorUsuario() {
    const busca = document.getElementById("pesquisa").value;

    if (busca.trim() === '') {
        Swal.fire({
            icon: 'warning',
            title: 'Atenção',
            text: 'Por favor, insira um valor para buscar.'
        });
        return;
    }

    const urlChat = `http://localhost:8080/mensagens/chat/${encodeURIComponent(busca)}`;
    const urlUsuario = `http://localhost:8080/mensagens/usuario/${encodeURIComponent(busca)}`;

    try {
        const [responseChat, responseUsuario] = await Promise.all([
            fetch(urlChat, { method: 'GET', headers: { 'Content-Type': 'application/json' } }),
            fetch(urlUsuario, { method: 'GET', headers: { 'Content-Type': 'application/json' } })
        ]);

        if (!responseChat.ok && !responseUsuario.ok) {
            Swal.fire({
                icon: 'info',
                title: 'Resultado da Busca',
                text: 'Nenhuma mensagem encontrada para o chat ou usuário especificado.'
            });
            return;
        }

        const dataChat = await responseChat.json();
        const dataUsuario = await responseUsuario.json();

        const mensagensChat = Array.isArray(dataChat) ? dataChat : [dataChat];
        const mensagensUsuario = Array.isArray(dataUsuario) ? dataUsuario : [dataUsuario];

        if (mensagensChat.length === 0 && mensagensUsuario.length === 0) {
            Swal.fire({
                icon: 'info',
                title: 'Resultado da Busca',
                text: 'Nenhuma mensagem encontrada para o chat ou usuário especificado.'
            });
            return;
        }

        const tabelaLista = document.getElementById('tabela_lista').getElementsByTagName('tbody')[0];
        tabelaLista.innerHTML = '';

        function adicionarMensagens(mensagens) {
            mensagens.forEach(mensagem => {
                const row = tabelaLista.insertRow();
                row.insertCell(0).textContent = mensagem.idMensagem;
                row.insertCell(1).textContent = mensagem.textoMensagem;
                row.insertCell(2).textContent = mensagem.dataMensagem;
                row.insertCell(3).textContent = mensagem.usuarioMensagem;
                row.insertCell(4).textContent = mensagem.chatMensagem;

                // Botão Excluir
                const cellExcluir = document.createElement('td');
                const btnExcluir = document.createElement('button');
                btnExcluir.textContent = 'Excluir';
                btnExcluir.className = 'btn btn-danger';
                btnExcluir.onclick = () => excluirMensagem(mensagem.idMensagem);
                cellExcluir.appendChild(btnExcluir);
                row.appendChild(cellExcluir);
            });
        }

        adicionarMensagens(mensagensChat);
        adicionarMensagens(mensagensUsuario);

    } catch (error) {
        Swal.fire({
            icon: 'error',
            title: 'Atenção',
            text: `Erro ao buscar mensagens: ${error.message}`
        });
    }
}

    // Função para excluir uma mensagem
    function excluirMensagem(id) {
        const url = `http://localhost:8080/mensagens/${id}`;
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
            confirmButtonText: 'Sim, excluir!',
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.isConfirmed) {
                fetch(url, requestOptions)
                    .then(response => {
                        if (!response.ok) {
                            throw new Error(`Falha ao excluir a mensagem. Código de status: ${response.status}`);
                        }

                        Swal.fire({
                            icon: 'success',
                            title: 'Excluído!',
                            text: 'A mensagem foi excluída com sucesso.'
                        });
                        carregarListaMensagens();
                    })
                    .catch(error => {
                        Swal.fire({
                            icon: 'error',
                            title: 'Erro',
                            text: 'Erro ao tentar excluir a mensagem.'
                        });
                        carregarListaMensagens();
                    });
            }
        });
    }

    document.getElementById('buttonPesquisar').onclick = function () {
        carregarMensagensPorUsuario();
    };
    document.getElementById('buttonApagar').onclick = function () {
        carregarListaMensagens();
        document.getElementById('pesquisa').value = "";
    };