/* Active nav link */
document.addEventListener('DOMContentLoaded', () => {
    const path = window.location.pathname;
    document.querySelectorAll('.nav-link').forEach(link => {
        const href = link.getAttribute('href');
        if (href === path || (path.startsWith(href) && href !== '/')) {
            link.classList.add('active');
        } else if (href === '/' && path === '/') {
            link.classList.add('active');
        }
    });
});

/* Copy to clipboard */
function copyToClipboard(text, btn) {
    navigator.clipboard.writeText(text).then(() => {
        const orig = btn.innerHTML;
        btn.innerHTML = '<i class="ti ti-check"></i> Đã sao chép';
        btn.classList.add('text-success');
        btn.classList.remove('text-muted');
        setTimeout(() => {
            btn.innerHTML = orig;
            btn.classList.remove('text-success');
        }, 2200);
    });
}

/* Random small prime suggestion for RSA */
const SMALL_PRIMES = [
    11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47,
    53, 59, 61, 67, 71, 73, 79, 83, 89, 97,
    101, 103, 107, 109, 113
];

function suggestPrime(inputId) {
    const input = document.getElementById(inputId);
    if (!input) return;
    const prime = SMALL_PRIMES[Math.floor(Math.random() * SMALL_PRIMES.length)];
    input.value = prime;
    input.focus();
    input.classList.add('highlight-flash');
    setTimeout(() => input.classList.remove('highlight-flash'), 600);
}

/* Reset form */
function resetForm(formId) {
    const form = document.getElementById(formId);
    if (form) form.reset();
}
