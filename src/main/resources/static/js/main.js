(() => {
    function setLayout() {
        document.querySelector(".container").style.minHeight = `${window.innerHeight}px`;
        if (window.innerWidth < 1024) {
            let loginContentFieldHeight = document.querySelector(".shortening-content-field").offsetHeight;
            document.querySelector(".shortening-section").style.minWidth = `${window.innerWidth}px`;
            document.querySelector(".shortening-content").style.minWidth = `${window.innerWidth}px`;
            document.querySelector(".shortening-content").style.minHeight = `${window.innerHeight}px`;
            document.querySelector(".shortening-content-field").style.paddingTop = `${(window.innerHeight - loginContentFieldHeight) / 2}px`;
        } else {
            document.querySelector(".shortening-section").style.minWidth = `500px`;
            document.querySelector(".shortening-content").style.minWidth = `500px`;
            document.querySelector(".shortening-content").style.minHeight = `100%`;
            document.querySelector(".shortening-content-field").style.paddingTop = `0`;
        }
    }

    window.addEventListener('load', () => {
        setLayout();
    });

    window.addEventListener('resize', () => {
        setLayout();
    })
})();