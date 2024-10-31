document.addEventListener('DOMContentLoaded', function () {
    carregarListaComentarios();
});

function carregarListaComentarios() {

    fetch('http://localhost:8080/comentarios')
        .then(response => response.json())
        .then(comentarios => {
            const tabelaLista = document.getElementById('tabela_lista').getElementsByTagName('tbody')[0];
            tabelaLista.innerHTML = '';
            comentarios.forEach(comentario => {
                const row = tabelaLista.insertRow();
                row.insertCell(0).textContent = comentario.idComentario;
                row.insertCell(1).textContent = comentario.emailUsuarioComent;
                row.insertCell(2).textContent = comentario.usuarioComent;
                row.insertCell(3).textContent = comentario.id_livroComent;
                row.insertCell(4).textContent = comentario.conteudoComent;
                row.insertCell(5).textContent = comentario.dataComentario;
                
                
                

                const cellExcluir = document.createElement('td');

                // Botão Excluir
                const btnExcluir = document.createElement('button');
                btnExcluir.textContent = 'Excluir';
                btnExcluir.className = 'btn btn-danger';
                btnExcluir.onclick = function () {
                    excluirComentario(comentario.idComentario);
                };
                cellExcluir.appendChild(btnExcluir);
                row.appendChild(cellExcluir);
            });
        })
        .catch(error => console.error('Erro ao carregar lista:', error));
}

async function carregarComentariosPorUsuario() {
    const busca = document.getElementById("pesquisa").value;

    if (busca.trim() === '') {
        Swal.fire({
            icon: 'warning',
            title: 'Atenção',
            text: 'Por favor, insira um valor para buscar.'
        });
        return;
    }

    const urlUsuario = `http://localhost:8080/comentarios/usuario/${encodeURIComponent(busca)}`;

    try {
        const responseUsuario = await fetch(urlUsuario, { method: 'GET', headers: { 'Content-Type': 'application/json' } });

        if (!responseUsuario.ok) {
            Swal.fire({
                icon: 'info',
                title: 'Resultado da Busca',
                text: 'Nenhum comentário encontrado para o usuário especificado.'
            });
            return;
        }

        const dataUsuario = await responseUsuario.json();

        const comentariosUsuario = Array.isArray(dataUsuario) ? dataUsuario : [dataUsuario];

        if (comentariosUsuario.length === 0) {
            Swal.fire({
                icon: 'info',
                title: 'Resultado da Busca',
                text: 'Nenhum comentário encontrado para o usuário especificado.'
            });
            return;
        }

        const tabelaLista = document.getElementById('tabela_lista').getElementsByTagName('tbody')[0];
        tabelaLista.innerHTML = '';

        function adicionarComentarios(comentarios) {
            comentarios.forEach(comentario => {
                const row = tabelaLista.insertRow();
                row.insertCell(0).textContent = comentario.idComentario;
                row.insertCell(1).textContent = comentario.emailUsuarioComent;
                row.insertCell(2).textContent = comentario.usuarioComent;
                row.insertCell(3).textContent = comentario.id_livroComent;
                row.insertCell(4).textContent = comentario.conteudoComent;
                row.insertCell(5).textContent = comentario.dataComentario;

                // Botão Excluir
                const cellExcluir = document.createElement('td');
                const btnExcluir = document.createElement('button');
                btnExcluir.textContent = 'Excluir';
                btnExcluir.className = 'btn btn-danger';
                btnExcluir.onclick = () => excluirComentario(comentario.idComentario);
                cellExcluir.appendChild(btnExcluir);
                row.appendChild(cellExcluir);
            });
        }

        adicionarComentarios(comentariosUsuario);

    } catch (error) {
        Swal.fire({
            icon: 'error',
            title: 'Atenção',
            text: `Erro ao buscar comentários: ${error.message}`
        });
    }
}

// Função para excluir um comentário
function excluirComentario(id) {
    const url = `http://localhost:8080/comentarios/${id}`;
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
                        throw new Error(`Falha ao excluir o comentário. Código de status: ${response.status}`);
                    }

                    Swal.fire({
                        icon: 'success',
                        title: 'Excluído!',
                        text: 'O comentário foi excluído com sucesso.'
                    });
                    carregarListaComentarios();
                })
                .catch(error => {
                    Swal.fire({
                        icon: 'error',
                        title: 'Erro',
                        text: 'Erro ao tentar excluir o comentário.'
                    });
                    carregarListaComentarios();
                });
        }
    });
}

document.getElementById('buttonPesquisar').onclick = function () {
    carregarComentariosPorUsuario();
};
document.getElementById('buttonApagar').onclick = function () {
    carregarListaComentarios();
    document.getElementById('pesquisa').value = "";
};
