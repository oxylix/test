const mongoose = require('mongoose');
const Schema = mongoose.Schema;

// Define collection and schema for Business
let Account = new Schema({
  user_id: { type: String },
  account_name: { type: String },
  account_email: { type: String },
  phone_number: { type: String },
  address: { type: String },
  zip_code: { type: String },
  country: { type: String },
  last_modification: { type: Date }
},{
    collection: 'account'
});

module.exports = mongoose.model('Account', Account);