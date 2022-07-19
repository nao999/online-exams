'use strict'
const path = require('path')

function resolve (dir) {
    return path.join(__dirname, dir)
}

module.exports = [
    {
        test: /\.svg$/,
        loader: 'svg-sprite-loader',
        include: [resolve('src/icons')],
        options: {
            symbolId: 'icon-[name]'
        }
    }
]
