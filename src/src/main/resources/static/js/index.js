
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
        window.location.href = 'register-candidate-image'; // Example navigation
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
document.addEventListener('DOMContentLoaded', function() {
    // Your code that accesses 'login' element goes here
    document.getElementById('delete-candidate').addEventListener('click', function () {
        window.location.href = 'search-candidates'; // Example navigation
    });
});
document.addEventListener('DOMContentLoaded', function() {
    // Your code that accesses 'login' element goes here
    document.getElementById('go-to-next-election').addEventListener('click', function () {
        window.location.href = 'election2'; // Example navigation
    });
});
document.addEventListener('DOMContentLoaded', function() {
    // Your code that accesses 'login' element goes here
    document.getElementById('go-to-next-review').addEventListener('click', function () {
        window.location.href = 'review'; // Example navigation
    });
});


// const delete-candidate = (candidateId) => {
//     fetch(`/candidates/${candidateId}`, {
//         method: 'DELETE'
//     })
//         .then(response => {
//             if (response.ok) {
//                 console.log('Candidate deleted successfully');
//                 // Handle success scenario
//             } else {
//                 console.error('Failed to delete candidate');
//                 // Handle error scenario
//             }
//         })
//         .catch(error => {
//             console.error('Error:', error);
//             // Handle network error scenario
//         });
// };

