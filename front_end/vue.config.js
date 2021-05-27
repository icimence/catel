module.exports = {
    devServer: {
        port: 8000,
    },
    css: {
        loaderOptions: {
            less: {
                modifyVars: {
                    'primary-color': '#622cb7',
                    'link-color': '#000000',
                    'border-radius-base': '8px',
                },
                javascriptEnabled: true,
            },
        },
    },
};
