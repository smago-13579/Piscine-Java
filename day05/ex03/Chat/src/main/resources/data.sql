INSERT INTO chat.user(name, password) VALUES
('Smago','smago'), ('Tom','tom'), ('Nick','nick'), ('Jazzy','jazzy'), ('Billy','billy'), ('Artur','artur')
ON CONFLICT DO NOTHING;

INSERT INTO chat.chatroom(title, owner) VALUES
('Chat1', (SELECT id FROM chat.user WHERE name = 'Smago')),
('Chat2', (SELECT id FROM chat.user WHERE name = 'Tom')),
('Chat3', (SELECT id FROM chat.user WHERE name = 'Nick')),
('Chat4', (SELECT id FROM chat.user WHERE name = 'Jazzy')),
('Chat5', (SELECT id FROM chat.user WHERE name = 'Billy')),
('Chat6', (SELECT id FROM chat.user WHERE name = 'Artur'))
ON CONFLICT DO NOTHING;

INSERT INTO chat.message (author, room, text) VALUES
((SELECT id FROM chat.user WHERE name = 'Smago'), (SELECT id FROM chat.chatroom WHERE title = 'Chat1'), 'Message1'),
((SELECT id FROM chat.user WHERE name = 'Tom'), (SELECT id FROM chat.chatroom WHERE title = 'Chat2'), 'Message2'),
((SELECT id FROM chat.user WHERE name = 'Nick'), (SELECT id FROM chat.chatroom WHERE title = 'Chat3'), 'Message3'),
((SELECT id FROM chat.user WHERE name = 'Jazzy'), (SELECT id FROM chat.chatroom WHERE title = 'Chat4'), 'Message4'),
((SELECT id FROM chat.user WHERE name = 'Billy'), (SELECT id FROM chat.chatroom WHERE title = 'Chat5'), 'Message5'),
((SELECT id FROM chat.user WHERE name = 'Artur'), (SELECT id FROM chat.chatroom WHERE title = 'Chat6'), 'Message6')
ON CONFLICT DO NOTHING;

INSERT INTO chat.user_chatroom (user_id, chat_id) VALUES
((SELECT id FROM chat.user WHERE name = 'Smago'), (SELECT id FROM chat.chatroom WHERE title = 'Chat1')),
((SELECT id FROM chat.user WHERE name = 'Tom'), (SELECT id FROM chat.chatroom WHERE title = 'Chat2')),
((SELECT id FROM chat.user WHERE name = 'Nick'), (SELECT id FROM chat.chatroom WHERE title = 'Chat3')),
((SELECT id FROM chat.user WHERE name = 'Jazzy'), (SELECT id FROM chat.chatroom WHERE title = 'Chat4')),
((SELECT id FROM chat.user WHERE name = 'Billy'), (SELECT id FROM chat.chatroom WHERE title = 'Chat5')),
((SELECT id FROM chat.user WHERE name = 'Artur'), (SELECT id FROM chat.chatroom WHERE title = 'Chat6'))
ON CONFLICT DO NOTHING;

