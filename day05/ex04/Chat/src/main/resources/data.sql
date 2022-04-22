INSERT INTO chat.user(name, password) VALUES
('Smago','smago'), ('Tom','tom'), ('Nick','nick'), ('Jazzy','jazzy'), ('Billy','billy'), ('Artur','artur'),
('User7', 'user7'), ('User8', 'user8'), ('User9', 'user9'), ('User10', 'user10'), ('User11', 'user11'), ('User12', 'user12'),
('User13', 'user13'), ('User14', 'user14'), ('User15', 'user15'), ('User16', 'user16'), ('User17', 'user17'), ('User18', 'user18')
ON CONFLICT DO NOTHING;

INSERT INTO chat.chatroom(title, owner) VALUES
('Chat1', (SELECT id FROM chat.user WHERE name = 'Smago')),
('Chat2', (SELECT id FROM chat.user WHERE name = 'Tom')),
('Chat3', (SELECT id FROM chat.user WHERE name = 'Nick')),
('Chat4', (SELECT id FROM chat.user WHERE name = 'Jazzy')),
('Chat5', (SELECT id FROM chat.user WHERE name = 'Billy')),
('Chat6', (SELECT id FROM chat.user WHERE name = 'Artur')),
('Chat7', (SELECT id FROM chat.user WHERE name = 'Smago')),
('Chat8', (SELECT id FROM chat.user WHERE name = 'Tom')),
('Chat9', (SELECT id FROM chat.user WHERE name = 'Nick')),
('Chat10', (SELECT id FROM chat.user WHERE name = 'Jazzy')),
('Chat11', (SELECT id FROM chat.user WHERE name = 'Smago')),
('Chat12', (SELECT id FROM chat.user WHERE name = 'Tom'))
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
((SELECT id FROM chat.user WHERE name = 'Smago'), (SELECT id FROM chat.chatroom WHERE title = 'Chat7')),
((SELECT id FROM chat.user WHERE name = 'Smago'), (SELECT id FROM chat.chatroom WHERE title = 'Chat11')),
((SELECT id FROM chat.user WHERE name = 'Smago'), (SELECT id FROM chat.chatroom WHERE title = 'Chat12')),
((SELECT id FROM chat.user WHERE name = 'Smago'), (SELECT id FROM chat.chatroom WHERE title = 'Chat3')),
((SELECT id FROM chat.user WHERE name = 'Tom'), (SELECT id FROM chat.chatroom WHERE title = 'Chat2')),
((SELECT id FROM chat.user WHERE name = 'Tom'), (SELECT id FROM chat.chatroom WHERE title = 'Chat8')),
((SELECT id FROM chat.user WHERE name = 'Tom'), (SELECT id FROM chat.chatroom WHERE title = 'Chat12')),
((SELECT id FROM chat.user WHERE name = 'Tom'), (SELECT id FROM chat.chatroom WHERE title = 'Chat3')),
((SELECT id FROM chat.user WHERE name = 'Tom'), (SELECT id FROM chat.chatroom WHERE title = 'Chat4')),
((SELECT id FROM chat.user WHERE name = 'Nick'), (SELECT id FROM chat.chatroom WHERE title = 'Chat3')),
((SELECT id FROM chat.user WHERE name = 'Nick'), (SELECT id FROM chat.chatroom WHERE title = 'Chat9')),
((SELECT id FROM chat.user WHERE name = 'Nick'), (SELECT id FROM chat.chatroom WHERE title = 'Chat10')),
((SELECT id FROM chat.user WHERE name = 'Jazzy'), (SELECT id FROM chat.chatroom WHERE title = 'Chat4')),
((SELECT id FROM chat.user WHERE name = 'Jazzy'), (SELECT id FROM chat.chatroom WHERE title = 'Chat10')),
((SELECT id FROM chat.user WHERE name = 'Jazzy'), (SELECT id FROM chat.chatroom WHERE title = 'Chat11')),
((SELECT id FROM chat.user WHERE name = 'Billy'), (SELECT id FROM chat.chatroom WHERE title = 'Chat5')),
((SELECT id FROM chat.user WHERE name = 'Billy'), (SELECT id FROM chat.chatroom WHERE title = 'Chat6')),
((SELECT id FROM chat.user WHERE name = 'Artur'), (SELECT id FROM chat.chatroom WHERE title = 'Chat6')),
((SELECT id FROM chat.user WHERE name = 'Artur'), (SELECT id FROM chat.chatroom WHERE title = 'Chat7')),
(11, 1), (11, 2), (11, 3),
(12, 2), (12, 3), (12, 4),
(13, 3), (13, 4), (13, 5), (13, 6),
(14, 4), (14, 5), (14, 6), (14, 7),
(15, 5), (15, 6), (15, 7), (15, 8)
ON CONFLICT DO NOTHING;

