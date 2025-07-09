$(document).ready(function() {
    $('#formRestaurante').on('submit', function(e) {
        e.preventDefault();
        salvarRestaurante();
    });
});

function salvarRestaurante() {
    const restauranteData = {
        id: $("#id").val() ? parseInt($("#id").val()) : null,
        nome: $("#nome").val(),
        descricao: $("#descricao").val(),
        culinaria: $("#culinaria").val(),
        anoFundacao: parseInt($("#anoFundacao").val())
    };

    if (!restauranteData.nome || !restauranteData.culinaria || isNaN(restauranteData.anoFundacao)) {
        showAlert('Preencha todos os campos obrigatórios corretamente!', 'danger');
        return;
    }

    $.ajax({
        url: '/restaurantes/salvar',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(restauranteData),
        success: function(response) {
            showAlert('Restaurante salvo com sucesso!', 'success');
            setTimeout(() => {
                window.location.href = '/restaurantes';
            }, 1500);
        },
        error: function(xhr) {
            const errorMsg = xhr.responseJSON?.error || xhr.statusText;
            showAlert('Erro ao salvar restaurante: ' + errorMsg, 'danger');
            console.error('Detalhes do erro:', xhr.responseJSON);
        }
    });
}

function carregarRestaurantes() {
    $.ajax({
        url: '/restaurantes',
        type: 'GET',
        dataType: 'json',
        success: function(response) {
            if (!Array.isArray(response)) {
                console.error("Resposta inesperada:", response);
                showAlert('Erro ao carregar restaurantes: formato inválido', 'danger');
                return;
            }
            
            let tabela = $('#tabelaRestaurantes tbody');
            tabela.empty();
            
            response.forEach(restaurante => {
                tabela.append(`
                    <tr>
                        <td>${restaurante.nome}</td>
                        <td>${restaurante.culinaria}</td>
                        <td>
                            <a href="/restaurantes/${restaurante.id}">Detalhes</a>
                            <a href="/restaurantes/editar/${restaurante.id}">Editar</a>
                            <button onclick="deletarRestaurante(${restaurante.id})">Excluir</button>
                        </td>
                    </tr>
                `);
            });
        },
        error: function(xhr, status, error) {
            console.error("Erro na requisição:", error);
            showAlert('Erro ao carregar restaurantes!', 'danger');
        }
    });
}

function deletarRestaurante(id) {
    if (confirm('Tem certeza que deseja excluir este restaurante?')) {
        $.ajax({
            url: '/restaurantes/excluir/' + id,
            type: 'POST',
            success: function() {
                showAlert('Restaurante excluído com sucesso!', 'success');
                setTimeout(() => {
                    window.location.reload();
                }, 1000);
            },
            error: function(xhr) {
                showAlert('Erro ao excluir restaurante: ' + xhr.responseText, 'danger');
            }
        });
    }
}

function showAlert(message, type) {
    const alertHtml = `
        <div class="alert alert-${type} alert-dismissible fade show" role="alert">
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    `;
    $('#alert-container').html(alertHtml);
    
    setTimeout(() => {
        $('.alert').alert('close');
    }, 5000);
}