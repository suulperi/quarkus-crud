package io.sohlberg;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/persons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonResource {

    @Inject
    PersonRepository personRepository;

    @GET
    public List<Person> getAllPersons() {
        return personRepository.listAll();
    }

    @GET
    @Path("{id}")
    public Person getPerson(@PathParam("id") Long id) {
        return personRepository.findById(id);
    }

    @POST
    @Transactional
    public Response createPerson(Person person) {
        personRepository.persist(person);
        return Response.status(Response.Status.CREATED).entity(person).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Person updatePerson(@PathParam("id") Long id, Person person) {
        Person entity = personRepository.findById(id);
        if(entity == null) {
            throw new WebApplicationException("Person with id of " + id + " does not exist.", Response.Status.NOT_FOUND);
        }
        entity.name = person.name;
        entity.age = person.age;
        return entity;
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deletePerson(@PathParam("id") Long id) {
        Person entity = personRepository.findById(id);
        if(entity == null) {
            throw new WebApplicationException("Person with id of " + id + " does not exist.", Response.Status.NOT_FOUND);
        }
        personRepository.delete(entity);
        return Response.noContent().build();
    }
}
