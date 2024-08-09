package repository.impl;

import java.util.ArrayList;
import java.util.Collection;

import fileSystem.FileInterpreter;
import fileSystem.FileManagement;
import infra.User.UserFileConverter;
import model.dto.User.UserDto;
import model.entities.User.User;
import repository.IRepository;

public class UserRepository implements IRepository<User, Long> {

	private static Long SEQUENCE = 0L;

	private String userFileName = "user.csv";

	private final FileManagement fileManagement;
    private final FileInterpreter fileInterpreter;
    private final UserFileConverter userFileConverter;

    public UserRepository() {
        this.fileManagement = new FileManagement();
        this.fileInterpreter = new FileInterpreter();
        this.userFileConverter = new UserFileConverter();
    }

	@Override
	public void save(User user) {
		if(user.getId() == null) {
			user.setId(++SEQUENCE);
		}

		deleteById(user.getId());
		UserDto userDto = new UserDto(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getType());
        fileManagement.write(userDto, userFileName);

	}

	@Override
	public void saveAll(Collection<User> collection) {
		for (User user : collection) {
            save(user);
        }

	}

	@Override
	public User findById(Long identifier) {
		Collection<User> users = findAll();
        for (User user : users) {
            if(user.getId().equals(identifier)){
                return user;
            }
        }
        return null;
	}

	@Override
	public Collection<User> findAll() {
		Collection<UserDto> usersDto = userFileConverter.all(fileInterpreter.interpret(fileManagement.read(userFileName), UserDto.class));

		Collection<User> users = new ArrayList<>();
		usersDto.forEach( dto -> users.add(this.generate(dto)) );

		return users;
	}

	@Override
	public void deleteById(Long identifier) {
		Collection<User> users = findAll();
        fileManagement.clear(userFileName);
        users.removeIf(user -> user.getId().equals(identifier));
        saveAll(users);
	}
	
	public User findByEmail(String email) {
		Collection<User> users = findAll();
        for (User user : users) {
            if(user.getEmail().equals(email)){
                return user;
            }
        }
        return null;
	}

	private User generate(UserDto userDto) {
		return new User(userDto.getId(), userDto.getName(), userDto.getEmail(), userDto.getPassword(), userDto.getType());
	}

}
