
document.addEventListener('DOMContentLoaded', function() {
    // Your code that accesses 'login' element goes here
    document.getElementById('login').addEventListener('click', function () {
        window.location.href = 'login'; // Example navigation
    });
});
document.addEventListener('DOMContentLoaded', function() {
    // Your code that accesses 'login' element goes here
    document.getElementById('register').addEventListener('click', function () {
        window.location.href = 'register'; // Example navigation
    });
});document.addEventListener('DOMContentLoaded', function() {
    // Your code that accesses 'login' element goes here
    document.getElementById('create-election').addEventListener('click', function () {
        window.location.href = 'set-election'; // Example navigation
    });
});
document.addEventListener('DOMContentLoaded', function() {
    // Your code that accesses 'login' element goes here
    document.getElementById('update-election').addEventListener('click', function () {
        window.location.href = 'search-election'; // Example navigation
    });
});
document.addEventListener('DOMContentLoaded', function() {
    // Your code that accesses 'login' element goes here
    document.getElementById('create-candidate').addEventListener('click', function () {
        window.location.href = 'register-candidate'; // Example navigation
    });
});document.addEventListener('DOMContentLoaded', function() {
    // Your code that accesses 'login' element goes here
    document.getElementById('results').addEventListener('click', function(){
        window.location.href = 'viewResults';
    });
});
document.addEventListener('DOMContentLoaded', function() {
    // Your code that accesses 'login' element goes here
    document.getElementById('update-info').addEventListener('click', function () {
        window.location.href = 'confirmInfo'; // Example navigation
    });
 });
document.addEventListener('DOMContentLoaded', function() {
    // Your code that accesses 'login' element goes here
    document.getElementById('update-profile').addEventListener('click', function () {
        window.location.href = 'confirmInfo'; // Example navigation
    });
});
document.addEventListener('DOMContentLoaded', function() {
    // Your code that accesses 'login' element goes here
    document.getElementById('go-to-ballot').addEventListener('click', function () {
        window.location.href = 'election'; // Example navigation
    });
});
document.addEventListener('DOMContentLoaded', function() {
    // Your code that accesses 'login' element goes here
    document.getElementById('candidate-info').addEventListener('click', function () {
        window.location.href = 'candidates-info-image'; // Example navigation
    });
});
function deleteCandidate(id) {
    if (confirm("Are you sure you want to delete this candidate?")) {
        fetch(`/search-candidates/${id}`, {
            method: 'DELETE'
        })
            .then(response => {
                if (response.ok) {
                    window.location.href = '/search-candidates?success';
                } else {
                    console.error('Failed to delete candidate.');
                }
            })
            .catch(error => console.error('Error:', error));
    }
}
