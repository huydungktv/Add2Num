document.querySelectorAll('.number-input').forEach((input) => {
    input.addEventListener('input', () => {
        input.value = input.value.replace(/[^0-9]/g, '');
    });
});

document.querySelectorAll('.process-table tbody tr').forEach((row, index) => {
    row.style.opacity = '0';
    row.style.transform = 'translateY(8px)';
    row.animate([
        { opacity: 0, transform: 'translateY(8px)' },
        { opacity: 1, transform: 'translateY(0)' }
    ], { duration: 260, delay: index * 70, fill: 'forwards', easing: 'ease-out' });
});
