import Joi from "joi";
import { PrismaClient } from "@prisma/client";

const prisma = new PrismaClient();

const schema = Joi.object({
  make: Joi.string().required(),
  model: Joi.string().required(),
  name: Joi.string().required(),
  year: Joi.number()
    .min(1886)
    .max(new Date().getFullYear() + 1)
    .required(),
  miles: Joi.number().required(),
  city: Joi.string().min(2).required(),
  numberOfSeats: Joi.number().max(1000).required(),
  features: Joi.array().items(Joi.string()).required(),
  image: Joi.string().required(),
  listerId: Joi.string().required(),
  price: Joi.number().min(0).required(),
  description: Joi.string().min(20).required(),
});

export default defineEventHandler(async (event) => {
  const body = await readBody(event);

  const { error, value } = await schema.validate(body);

  if (error) {
    throw createError({
      statusCode: 412,
      statusMessage: error.message,
    });
  }

  const {
    image,
    name,
    numberOfSeats,
    miles,
    price,
    features,
    description,
    listerId,
    city,
    make,
    model,
  } = body;

  return  await prisma.car.create({
    data: {
      name,
      numberOfSeats,
      miles,
      price,
      image,
      features,
      description,
      city: city.toLowerCase(),
      model,
      make,
      listerId,
    },
  });
});
