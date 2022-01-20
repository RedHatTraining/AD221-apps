const fastify = require('fastify')({ logger: true });

const index = {
    "description": "Temperatures service",
    "endpoints": {
        "temperatures": "/temperatures"
    }
};
const temperatures = [7.6, 15.2, 24.5, 19.5];


fastify
    .get("/", async() => index)
    .get("/temperatures", async () => temperatures)
    .listen(3000, "0.0.0.0")
    .catch((error) => {
        fastify.log.error(err);
        process.exit(1);
    });
