const express = require('express');

const app = express();

app.get('/liveness',(req,res)=> {
    res.status(200);
});

app.listen(8080, '0.0.0.0');