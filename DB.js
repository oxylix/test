var port = process.env.OPENSHIFT_MONGODB_PORT || 27017,
    mongoHOST = process.env.OPENSHIFT_MONGODB_DB_URL || 'localhost';

module.exports = {
    DB: 'mongodb://'+mongoHOST+':'+port+'/backend_db'
 };