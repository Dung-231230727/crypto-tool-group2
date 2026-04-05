/* Sidebar Toggle & Persistence */
function toggleSidebar() {
    const body = document.body;
    const isMobile = window.innerWidth <= 991.98;
    
    if (isMobile) {
        body.classList.toggle('sidebar-open');
    } else {
        body.classList.toggle('sidebar-collapsed');
        // Persist desktop preference
        localStorage.setItem('sidebar-collapsed', body.classList.contains('sidebar-collapsed'));
    }
}

// Global initialization to prevent flicker
(function() {
    if (window.innerWidth > 991.98) {
        const isCollapsed = localStorage.getItem('sidebar-collapsed') === 'true';
        if (isCollapsed) {
            // Apply class to document element early
            document.documentElement.classList.add('sidebar-collapsed-init');
        }
    }
})();

// Close sidebar on mobile when a link is clicked
function handleNavLinkClick() {
    if (window.innerWidth <= 991.98) {
        document.body.classList.remove('sidebar-open');
    }
}

document.addEventListener('DOMContentLoaded', () => {
    // Handover from immediate state (html) to interactive state (body)
    const htmlClass = document.documentElement.classList;
    if (htmlClass.contains('sidebar-collapsed-init')) {
        document.body.classList.add('sidebar-collapsed');
        htmlClass.remove('sidebar-collapsed-init');
    }

    // Active nav link & click handler
    const path = window.location.pathname;
    document.querySelectorAll('.nav-link').forEach(link => {
        const href = link.getAttribute('href');
        
        // Add click listener to close sidebar on mobile
        link.addEventListener('click', handleNavLinkClick);

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
