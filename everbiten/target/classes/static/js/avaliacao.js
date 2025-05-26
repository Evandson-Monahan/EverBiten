$(document).ready(function() {
    $('#formAvaliacao').on('submit', function(e) {
        e.preventDefault();
        enviarAvaliacao();
    });
});

function enviarAvaliacao() {
    const avaliacao = {
        restauranteId: $('#restauranteId').val(),
        comentario: $('#comentario').val(),
        nota: $('#nota').val()
    };

    if (!avaliacao.comentario || !avaliacao.nota) {
        alert('Preencha todos os campos obrigatórios!');
        return;
    }

    $.ajax({
        url: '/avaliacoes',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(avaliacao),
        success: function() {
            $('#comentario').val('');
            $('#nota').val('');
            location.reload();
        },
        error: function(xhr) {
            const errorMsg = xhr.responseJSON?.message || 'Erro desconhecido';
            alert('Erro ao enviar avaliação: ' + errorMsg);
        }
    });
}