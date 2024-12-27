document.querySelectorAll('.tab')
    .forEach(
        (tab, index) => {
            tab.addEventListener('click', () => {
                    // Remove active class from all tabs and content
                    document.querySelectorAll('.tab')
                        .forEach(t => t.classList.remove('active'));
                    document.querySelectorAll('.tab-content')
                        .forEach(c => c.classList.remove('active'));

                    // Add active class to clicked tab and corresponding content
                    tab.classList.add('active');
                    document.querySelectorAll('.tab-content')[index].classList.add('active');
            });
    });