async function loginAdmin() {
    // Pega os valores dos campos
    const credencial = document.getElementById("credencial").value;
    const senhaadm = document.getElementById("senhaadm").value;

    try {
        // Envia a requisição para o backend
        const response = await fetch("http://localhost:8080/api/admin/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                credencial: credencial,
                senhaadm: senhaadm
            })
        });

        // Verifica o status da resposta
        const result = await response.json();
        const mensagemElement = document.getElementById("mensagem");

        if (response.ok) {
            // Login bem-sucedido
            mensagemElement.textContent = result.message;
            mensagemElement.style.color = "green";
            window.location.href = "Opcoes.html";
        } else {
            // Falha no login
            mensagemElement.textContent = result.message;
            mensagemElement.style.color = "red";
        }
    } catch (error) {
        console.error("Erro na requisição:", error);
        document.getElementById("mensagem").textContent = "Erro ao tentar fazer login.";
    }
}